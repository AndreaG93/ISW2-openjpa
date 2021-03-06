/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.openjpa.jdbc.meta.strats;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import org.apache.openjpa.jdbc.kernel.EagerFetchModes;
import org.apache.openjpa.jdbc.kernel.JDBCFetchConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.Result;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.jdbc.sql.Union;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.JavaTypes;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.MetaDataException;
import org.apache.openjpa.util.Proxy;

/**
 * Uses an association table to hold map values. Derives map keys from
 * a field in each value.
 *
 * @author Abe White
 */
public class RelationMapTableFieldStrategy
    extends RelationToManyTableFieldStrategy
    implements LRSMapFieldStrategy {

    
    private static final long serialVersionUID = 1L;
    private static final Localizer _loc = Localizer.forPackage
        (RelationMapTableFieldStrategy.class);

    @Override
    public FieldMapping getFieldMapping() {
        return field;
    }

    @Override
    public ClassMapping[] getIndependentKeyMappings(boolean traverse) {
        return ClassMapping.EMPTY_MAPPINGS;
    }

    @Override
    public ClassMapping[] getIndependentValueMappings(boolean traverse) {
        return getIndependentElementMappings(traverse);
    }

    @Override
    public ForeignKey getJoinForeignKey(ClassMapping cls) {
        return super.getJoinForeignKey(cls);
    }

    @Override
    public Column[] getKeyColumns(ClassMapping cls) {
        return cls.getFieldMapping(field.getKey().
            getValueMappedByMetaData().getIndex()).getColumns();
    }

    @Override
    public Column[] getValueColumns(ClassMapping cls) {
        return field.getElementMapping().getColumns();
    }

    @Override
    public void selectKey(Select sel, ClassMapping key, OpenJPAStateManager sm,
        JDBCStore store, JDBCFetchConfiguration fetch, Joins joins) {
        ValueMapping vm = field.getKeyMapping();
        if (vm.isEmbedded())
            sel.select(key, field.getKeyMapping().getSelectSubclasses(),
                store, fetch, EagerFetchModes.EAGER_NONE, joins);
        else
            throw new InternalException();
    }

    @Override
    public Object loadKey(OpenJPAStateManager sm, JDBCStore store,
        JDBCFetchConfiguration fetch, Result res, Joins joins)
        throws SQLException {
        ValueMapping vm = field.getKeyMapping();
        if (vm.isEmbedded())
            return vm.getValueMappedByMapping().
                loadProjection(store, fetch, res, joins);
        else
            throw new InternalException();
    }

    @Override
    public Object deriveKey(JDBCStore store, Object value) {
        OpenJPAStateManager sm = RelationStrategies.getStateManager(value,
            store.getContext());
        return (sm == null) ? null : sm.fetchField(field.getKey().
            getValueMappedByMetaData().getIndex(), false);
    }

    @Override
    public Object deriveValue(JDBCStore store, Object key) {
        return null;
    }

    @Override
    public void selectValue(Select sel, ClassMapping val,
        OpenJPAStateManager sm, JDBCStore store, JDBCFetchConfiguration fetch,
        Joins joins) {
        selectElement(sel, val, store, fetch, EagerFetchModes.EAGER_NONE,
            joins);
    }

    @Override
    public Object loadValue(OpenJPAStateManager sm, JDBCStore store,
        JDBCFetchConfiguration fetch, Result res, Joins joins)
        throws SQLException {
        return loadElement(sm, store, fetch, res, joins);
    }

    @Override
    public Result[] getResults(final OpenJPAStateManager sm,
        final JDBCStore store, final JDBCFetchConfiguration fetch,
        final int eagerMode, final Joins[] joins, boolean lrs)
        throws SQLException {
        ValueMapping val = field.getElementMapping();
        final ClassMapping[] vals = val.getIndependentTypeMappings();
        Union union = store.getSQLFactory().newUnion(vals.length);
        if (fetch.getSubclassFetchMode(val.getTypeMapping())
            != EagerFetchModes.EAGER_JOIN)
            union.abortUnion();
        union.setLRS(lrs);
        union.select(new Union.Selector() {
            @Override
            public void select(Select sel, int idx) {
                joins[1] = selectAll(sel, vals[idx], sm, store, fetch,
                    eagerMode);
            }
        });
        Result res = union.execute(store, fetch);
        return new Result[]{ res, res };
    }

    @Override
    public Joins joinKeyRelation(Joins joins, ClassMapping key) {
        return joins;
    }

    @Override
    public Joins joinKeyRelation(Joins joins, boolean forceOuter,
        boolean traverse) {
        ValueMapping key = field.getKeyMapping();
        if (key.isEmbedded())
            return joins;

        ClassMapping[] clss = key.getIndependentTypeMappings();
        if (clss.length != 1) {
            if (traverse)
                throw RelationStrategies.unjoinable(key);
            return joins;
        }
        if (forceOuter)
            return joins.outerJoinRelation(field.getName(),
                key.getForeignKey(clss[0]), clss[0], key.getSelectSubclasses(),
                false, false);
        return joins.joinRelation(field.getName(),
            key.getForeignKey(clss[0]), clss[0], key.getSelectSubclasses(),
            false, false);
    }

    @Override
    public Joins joinValueRelation(Joins joins, ClassMapping val) {
        return joinElementRelation(joins, val);
    }

    @Override
    protected Proxy newLRSProxy() {
        return new LRSProxyMap(this);
    }

    @Override
    protected void add(JDBCStore store, Object coll, Object obj) {
        if (obj != null)
            ((Map) coll).put(deriveKey(store, obj), obj);
    }

    @Override
    protected Collection toCollection(Object val) {
        return (val == null) ? null : ((Map) val).values();
    }

    @Override
    public void map(boolean adapt) {
        if (field.getTypeCode() != JavaTypes.MAP)
            throw new MetaDataException(_loc.get("not-map", field));
        if (field.getKey().getValueMappedBy() == null)
            throw new MetaDataException(_loc.get("not-mapped-by-key", field));
        super.map(adapt);
    }

    @Override
    public Joins joinKey(Joins joins, boolean forceOuter) {
        return joinRelation(join(joins, forceOuter), forceOuter, false);
    }
}
