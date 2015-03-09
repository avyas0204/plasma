package org.plasma.provisioning.rdb.mysql.v5_5.query;

import org.plasma.provisioning.rdb.mysql.v5_5.TableConstraint;
import org.plasma.query.DataProperty;
import org.plasma.query.Expression;
import org.plasma.query.dsl.DataNode;
import org.plasma.query.dsl.DomainRoot;
import org.plasma.query.dsl.PathNode;
import org.plasma.sdo.helper.PlasmaTypeHelper;

/**
 * Generated Domain Specific Language (DSL) implementation class representing the domain model entity <b>TableConstraint</b>.
 *
 * <p></p>
 * <b>Data Store Mapping:</b>
 * Corresponds to the physical data store entity <b>TABLE_CONSTRAINTS</b>.
 *
 */
public class QTableConstraint extends DomainRoot
{


	private QTableConstraint() {
		super(PlasmaTypeHelper.INSTANCE.getType(TableConstraint.class));
	}
	
	/**
	 * Constructor which instantiates a domain query path node. A path may
	 * span multiple namespaces and therefore Java inplementation packages
	 * based on the <a href="http://docs.plasma-sdo.org/api/org/plasma/config/PlasmaConfiguration.html">Condiguration</a>.
	 * Note: while this constructor is public, it is not for application use!
	 * @param source the source path node
	 * @param sourceProperty the source property logical name
	 */
	public QTableConstraint(PathNode source, String sourceProperty) {
		super(source, sourceProperty);
	}
	
	/**
	 * Constructor which instantiates a domain query path node. A path may
	 * span multiple namespaces and therefore Java inplementation packages
	 * based on the <a href="http://docs.plasma-sdo.org/api/org/plasma/config/PlasmaConfiguration.html">Condiguration</a>.
	 * Note: while this constructor is public, it is not for application use!
	 * @param source the source path node
	 * @param sourceProperty the source property logical name
	 * @param expr the path predicate expression
	 */
	public QTableConstraint(PathNode source, String sourceProperty, Expression expr) {
		super(source, sourceProperty, expr);
	}

	/**
	 * Returns a new DSL query for <a href="http://docs.plasma-sdo.org/api/org/plasma/sdo/PlasmaType.html">Type</a> <b>TableConstraint</b> which can be used either as a query root or
	 * as the start (entry point) for a new path predicate expression.
	 * @return a new DSL query
	 */
	public static QTableConstraint newQuery() {
		return new QTableConstraint();
	}

	/**
	 * Returns a DSL data element for property, <b>constraintType</b>.
	 * @return a DSL data element for property, <b>constraintType</b>.
	 */
	public DataProperty constraintType() {
		return new DataNode(this, TableConstraint.PROPERTY.constraintType.name());
	}

	/**
	 * Returns a DSL data element for property, <b>name</b>.
	 * @return a DSL data element for property, <b>name</b>.
	 */
	public DataProperty name() {
		return new DataNode(this, TableConstraint.PROPERTY.name.name());
	}

	/**
	 * Returns a DSL data element for property, <b>owner</b>.
	 * @return a DSL data element for property, <b>owner</b>.
	 */
	public DataProperty owner() {
		return new DataNode(this, TableConstraint.PROPERTY.owner.name());
	}

	/**
	 * Returns a DSL query element for reference property, <b>table</b>.
	 * @return a DSL query element for reference property, <b>table</b>.
	 */
	public QTable table() {
		return new QTable(this, TableConstraint.PROPERTY.table.name());
	}
}