/**
 * Copyright 2017 TerraMeta Software, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.plasma.query.visitor;

import org.plasma.query.From;
import org.plasma.query.GroupBy;
import org.plasma.query.Having;
import org.plasma.query.OrderBy;
import org.plasma.query.Query;
import org.plasma.query.Where;
import org.plasma.query.model.AbstractProperty;
import org.plasma.query.model.ArithmeticOperator;
import org.plasma.query.model.Clause;
import org.plasma.query.model.Delete;
import org.plasma.query.model.Entity;
import org.plasma.query.model.Expression;
import org.plasma.query.model.GroupOperator;
import org.plasma.query.model.Join;
import org.plasma.query.model.Literal;
import org.plasma.query.model.LogicalOperator;
import org.plasma.query.model.NullLiteral;
import org.plasma.query.model.PredicateOperator;
import org.plasma.query.model.Property;
import org.plasma.query.model.RelationalOperator;
import org.plasma.query.model.Select;
import org.plasma.query.model.Term;
import org.plasma.query.model.Update;
import org.plasma.query.model.Variable;
import org.plasma.query.model.WildcardProperty;

/**
 * Contains "visit" start/end methods called by query traversal algorithms as
 * various structural nodes are encountered. Helps generalize and separate the
 * traversal logic from clients only needing notifications as various specific
 * nodes are traversed.
 */
public interface QueryVisitor {
  public VisitorContext getContext();

  public void start(Query query);

  public void start(Clause clause);

  public void start(Select select);

  public void start(Update update);

  public void start(Delete delete);

  public void start(From from);

  public void start(Where where);

  public void start(OrderBy orderBy);

  public void start(GroupBy groupBy);

  public void start(Having having);

  public void start(Join join);

  public void start(Expression expression);

  public void start(Term term);

  public void start(Property property);

  public void start(WildcardProperty property);

  public void start(AbstractProperty property);

  public void start(Entity entity);

  public void start(GroupOperator operator);

  public void start(RelationalOperator operator);

  public void start(LogicalOperator operator);

  public void start(ArithmeticOperator operator);

  public void start(PredicateOperator operator);

  public void start(Literal literal);

  public void start(NullLiteral literal);

  public void start(Variable variable);

  public void end(Query query);

  public void end(Clause clause);

  public void end(Select select);

  public void end(Update update);

  public void end(Delete delete);

  public void end(From from);

  public void end(Where where);

  public void end(OrderBy orderBy);

  public void end(GroupBy groupBy);

  public void end(Having having);

  public void end(Join join);

  public void end(Expression expression);

  public void end(Term term);

  public void end(Property property);

  public void end(WildcardProperty property);

  public void end(AbstractProperty property);

  public void end(Entity entity);

  public void end(GroupOperator operator);

  public void end(RelationalOperator operator);

  public void end(LogicalOperator operator);

  public void end(ArithmeticOperator operator);

  public void end(PredicateOperator operator);

  public void end(Literal literal);

  public void end(NullLiteral literal);

  public void end(Variable variable);
}
