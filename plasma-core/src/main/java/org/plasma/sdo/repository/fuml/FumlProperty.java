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

package org.plasma.sdo.repository.fuml;

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.fuml.repository.Stereotype;
import org.plasma.common.exception.PlasmaRuntimeException;
import org.plasma.sdo.Alias;
import org.plasma.sdo.Compression;
import org.plasma.sdo.Concurrent;
import org.plasma.sdo.Derivation;
import org.plasma.sdo.EnumerationConstraint;
import org.plasma.sdo.Increment;
import org.plasma.sdo.Key;
import org.plasma.sdo.Sort;
import org.plasma.sdo.Temporal;
import org.plasma.sdo.UniqueConstraint;
import org.plasma.sdo.ValueConstraint;
import org.plasma.sdo.ValueSetConstraint;
import org.plasma.sdo.XmlProperty;
import org.plasma.sdo.profile.ConcurrencyType;
import org.plasma.sdo.profile.ConcurrentDataFlavor;
import org.plasma.sdo.profile.KeyType;
import org.plasma.sdo.profile.SDOAlias;
import org.plasma.sdo.profile.SDOCompression;
import org.plasma.sdo.profile.SDOConcurrent;
import org.plasma.sdo.profile.SDODerivation;
import org.plasma.sdo.profile.SDOEnumerationConstraint;
import org.plasma.sdo.profile.SDOIncrement;
import org.plasma.sdo.profile.SDOKey;
import org.plasma.sdo.profile.SDOSort;
import org.plasma.sdo.profile.SDOTemporal;
import org.plasma.sdo.profile.SDOUniqueConstraint;
import org.plasma.sdo.profile.SDOValueConstraint;
import org.plasma.sdo.profile.SDOValueSetConstraint;
import org.plasma.sdo.profile.SDOXmlProperty;
import org.plasma.sdo.repository.Class_;
import org.plasma.sdo.repository.Classifier;
import org.plasma.sdo.repository.Comment;
import org.plasma.sdo.repository.Element;
import org.plasma.sdo.repository.Enumeration;
import org.plasma.sdo.repository.Property;
import org.plasma.sdo.repository.Visibility;

import fUML.Syntax.Classes.Kernel.VisibilityKind;

class FumlProperty extends FumlElement<org.modeldriven.fuml.repository.Property> implements
    Property {

  /**
   * Cached locally as the call into FUML RI property is goes into upperValue
   * etc... and is showing up in CPU profiling output.
   */
  private Boolean isMany;

  public FumlProperty(org.modeldriven.fuml.repository.Property property) {
    super(property);
  }

  public String toString() {
    String propName = this.element.getName();
    if (propName != null && propName.trim().length() > 0) {
      return this.element.getClass_().getName() + "#" + propName;
    } else {
      return this.element.getClass_().getName() + "#" + this.element.getType().getName();
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#findPropertyDefault()
   */
  @Override
  public Object findPropertyDefault() {
    return this.element.findPropertyDefault();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#isMany()
   */
  @Override
  public boolean isMany() {
    if (this.isMany == null) {
      this.isMany = !this.element.isSingular();
    }
    return this.isMany.booleanValue();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#isNullable()
   */
  @Override
  public boolean isNullable() {
    return !this.element.isRequired();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#getOpposite()
   */
  @Override
  public Property getOpposite() {
    org.modeldriven.fuml.repository.Property opposite = this.element.getOpposite();
    if (opposite != null)
      return new FumlProperty(opposite);

    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#getComments()
   */
  @Override
  public List<Comment> getComments() {
    List<Comment> result = new ArrayList<Comment>();
    for (fUML.Syntax.Classes.Kernel.Comment comment : element.getDelegate().ownedComment)
      result.add(new FumlComment(comment));
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#isDataType()
   */
  @Override
  public boolean isDataType() {
    return this.element.getType().isDataType();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#getVisibility()
   */
  @Override
  public Visibility getVisibility() {
    VisibilityKind v = this.element.getDelegate().visibility;
    if (v != null)
      return Visibility.valueOf(v.name());
    return Visibility.public_;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#getIsReadonly()
   */
  @Override
  public boolean getIsReadonly() {
    return this.element.getDelegate().isReadOnly;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#findPhysicalName()
   */
  @Override
  public String findPhysicalName() {
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        element);
    validate(stereotypes);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDOAlias) {
          SDOAlias sdoAliasStereotype = (SDOAlias) stereotype.getDelegate();
          if (sdoAliasStereotype.getPhysicalName() != null)
            return sdoAliasStereotype.getPhysicalName();
          else
            return null;
        }
    } else
      throw new PlasmaRuntimeException("no stereotypes found for property, '"
          + element.getClass_().getName() + "." + element.getName() + "'");
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#getLocalName()
   */
  @Override
  public String getLocalName() {
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        element);
    validate(stereotypes);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDOAlias) {
          SDOAlias sdoAliasStereotype = (SDOAlias) stereotype.getDelegate();
          if (sdoAliasStereotype.getLocalName() != null)
            return sdoAliasStereotype.getLocalName();
          else
            throw new PlasmaRuntimeException("expected 'localName' property for Stereotype, '"
                + sdoAliasStereotype.name + "'");
        }
    } else
      throw new PlasmaRuntimeException("no stereotypes found for property, '"
          + element.getClass_().getName() + "." + element.getName() + "'");
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#findAlias()
   */
  @Override
  public Alias findAlias() {
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDOAlias) {
          return (SDOAlias) stereotype.getDelegate();
        }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#findKey()
   */
  @Override
  public Key findKey() {
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDOKey) {
          return (SDOKey) stereotype.getDelegate();
        }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#findKeySupplier()
   */
  @Override
  public Property findKeySupplier() {
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDOKey) {
          SDOKey key = (SDOKey) stereotype.getDelegate();
          fUML.Syntax.Classes.Kernel.NamedElement namedElem = key.getSupplier();
          if (namedElem != null) {
            Element elem = FumlRepository.getFumlRepositoryInstance().getElementById(
                namedElem.getXmiId());
            if (elem instanceof Property) {
              return (Property) elem;
            }
          }
        }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#findConcurrent()
   */
  @Override
  public Concurrent findConcurrent() {
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDOConcurrent) {
          return (SDOConcurrent) stereotype.getDelegate();
        }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#findTemporal()
   */
  @Override
  public Temporal findTemporal() {
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDOTemporal) {
          return (SDOTemporal) stereotype.getDelegate();
        }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#findEnumerationConstraint()
   */
  @Override
  public EnumerationConstraint findEnumerationConstraint() {
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDOEnumerationConstraint) {
          return (SDOEnumerationConstraint) stereotype.getDelegate();
        }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#findValueSetConstraint()
   */
  @Override
  public ValueSetConstraint findValueSetConstraint() {
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDOValueSetConstraint) {
          return (SDOValueSetConstraint) stereotype.getDelegate();
        }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#findValueConstraint()
   */
  @Override
  public ValueConstraint findValueConstraint() {
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDOValueConstraint) {
          return (SDOValueConstraint) stereotype.getDelegate();
        }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#findSort()
   */
  @Override
  public Sort findSort() {
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDOSort) {
          return (SDOSort) stereotype.getDelegate();
        }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#findUniqueConstraint()
   */
  @Override
  public UniqueConstraint findUniqueConstraint() {
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDOUniqueConstraint) {
          return (SDOUniqueConstraint) stereotype.getDelegate();
        }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#findDerivationSupplier()
   */
  @Override
  public Property findDerivationSupplier() {
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDODerivation) {
          SDODerivation deriv = (SDODerivation) stereotype.getDelegate();
          fUML.Syntax.Classes.Kernel.NamedElement namedElem = deriv.getSupplier();
          if (namedElem != null) {
            Element elem = FumlRepository.getFumlRepositoryInstance().getElementById(
                namedElem.getXmiId());
            if (elem instanceof Property) {
              return (Property) elem;
            }
          }
        }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#findDerivation()
   */
  @Override
  public Derivation findDerivation() {
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDODerivation) {
          return (SDODerivation) stereotype.getDelegate();
        }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#getIsPriKey()
   */
  @Override
  public boolean getIsPriKey() {
    if (!element.isDataType())
      throw new IllegalArgumentException("property " + element.getClass_().getName() + "."
          + element.getName() + " is not a datatype property");
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDOKey) {
          SDOKey sdoKeyStereotype = (SDOKey) stereotype.getDelegate();
          return sdoKeyStereotype.getType().ordinal() == KeyType.primary.ordinal();
        }
    } else
      throw new PlasmaRuntimeException("no stereotypes found for property, '"
          + element.getClass_().getName() + "." + element.getName() + "'");
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#getMaxLength()
   */
  @Override
  public Long getMaxLength() {
    if (!element.isDataType())
      throw new IllegalArgumentException("property " + element.getClass_().getName() + "."
          + element.getName() + " is not a datatype property");
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDOValueConstraint) {
          SDOValueConstraint sdoValueConstraintStereotype = (SDOValueConstraint) stereotype
              .getDelegate();
          if (sdoValueConstraintStereotype.getMaxLength() != null)
            return Long.valueOf(sdoValueConstraintStereotype.getMaxLength());
        }
    } else
      throw new PlasmaRuntimeException("no stereotypes found for property, '"
          + element.getClass_().getName() + "." + element.getName() + "'");
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#getRestriction()
   */
  @Override
  public Enumeration getRestriction() {
    SDOEnumerationConstraint sdoEnumerationConstraintStereotype = (SDOEnumerationConstraint) this
        .findEnumerationConstraint();
    if (sdoEnumerationConstraintStereotype != null) {
      return FumlRepository.getFumlRepositoryInstance().getEnumerationById(
          sdoEnumerationConstraintStereotype.getValue().getXmiId());
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#findXmlProperty()
   */
  @Override
  public XmlProperty findXmlProperty() {
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDOXmlProperty) {
          return (SDOXmlProperty) stereotype.getDelegate();
        }
    }
    return null;
  }

  @Override
  public Increment findIncrement() {
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDOIncrement) {
          return (SDOIncrement) stereotype.getDelegate();
        }
    }
    return null;
  }

  @Override
  public Compression findCompression() {
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDOCompression) {
          return (SDOCompression) stereotype.getDelegate();
        }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#getIsConcurrencyUser()
   */
  @Override
  public boolean getIsConcurrencyUser() {
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDOConcurrent) {
          SDOConcurrent sdoConcurrentStereotype = (SDOConcurrent) stereotype.getDelegate();
          return sdoConcurrentStereotype.getType().ordinal() == ConcurrencyType.optimistic
              .ordinal()
              && sdoConcurrentStereotype.getDataFlavor().ordinal() == ConcurrentDataFlavor.user
                  .ordinal();
        }
    }
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#getIsConcurrencyVersion()
   */
  @Override
  public boolean getIsConcurrencyVersion() {
    if (!element.isDataType())
      throw new IllegalArgumentException("property " + element.getClass_().getName() + "."
          + element.getName() + " is not a datatype property");
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDOConcurrent) {
          SDOConcurrent sdoConcurrentStereotype = (SDOConcurrent) stereotype.getDelegate();
          return sdoConcurrentStereotype.getType().ordinal() == ConcurrencyType.optimistic
              .ordinal()
              && (sdoConcurrentStereotype.getDataFlavor().ordinal() == ConcurrentDataFlavor.version
                  .ordinal());
        }
    }
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#getIsLockingUser()
   */
  @Override
  public boolean getIsLockingUser() {
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDOConcurrent) {
          SDOConcurrent sdoConcurrentStereotype = (SDOConcurrent) stereotype.getDelegate();
          return sdoConcurrentStereotype.getDataFlavor().ordinal() == ConcurrentDataFlavor.user
              .ordinal()
              && sdoConcurrentStereotype.getType().ordinal() == ConcurrencyType.pessimistic
                  .ordinal();
        }
    }
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#getIsLockingTimestamp()
   */
  @Override
  public boolean getIsLockingTimestamp() {
    if (!element.isDataType())
      throw new IllegalArgumentException("property " + element.getClass_().getName() + "."
          + element.getName() + " is not a datatype property");
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDOConcurrent) {
          SDOConcurrent sdoConcurrentStereotype = (SDOConcurrent) stereotype.getDelegate();
          return sdoConcurrentStereotype.getDataFlavor().ordinal() == ConcurrentDataFlavor.time
              .ordinal()
              && sdoConcurrentStereotype.getType().ordinal() == ConcurrencyType.pessimistic
                  .ordinal();
        }
    }
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#getIsOriginationUser()
   */
  @Override
  public boolean getIsOriginationUser() {
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDOConcurrent) {
          SDOConcurrent sdoConcurrentStereotype = (SDOConcurrent) stereotype.getDelegate();
          return sdoConcurrentStereotype.getDataFlavor().ordinal() == ConcurrentDataFlavor.user
              .ordinal()
              && sdoConcurrentStereotype.getType().ordinal() == ConcurrencyType.origination
                  .ordinal();
        }
    }
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#getIsOriginationTimestamp()
   */
  @Override
  public boolean getIsOriginationTimestamp() {
    if (!element.isDataType())
      throw new IllegalArgumentException("property " + element.getClass_().getName() + "."
          + element.getName() + " is not a datatype property");
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDOConcurrent) {
          SDOConcurrent sdoConcurrentStereotype = (SDOConcurrent) stereotype.getDelegate();
          return sdoConcurrentStereotype.getDataFlavor().ordinal() == ConcurrentDataFlavor.time
              .ordinal()
              && sdoConcurrentStereotype.getType().ordinal() == ConcurrencyType.origination
                  .ordinal();
        }
    }
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.plasma.sdo.repository.fuml.Property#getIsUnique()
   */
  @Override
  public boolean getIsUnique() {
    List<Stereotype> stereotypes = FumlRepository.getFumlRepositoryInstance().getStereotypes(
        element);
    if (stereotypes != null) {
      for (Stereotype stereotype : stereotypes)
        if (stereotype.getDelegate() instanceof SDOUniqueConstraint) {
          // SDOUniqueConstraint sdoUniqueConstraintStereotype =
          // (SDOUniqueConstraint)stereotype.getDelegate();
          return true;
        }
    } else
      throw new PlasmaRuntimeException("no stereotypes found for property, '" + element.getName()
          + "'");
    return false;
  }

  private void validate(List<Stereotype> stereotypes) {

  }

  @Override
  public Classifier getType() {
    return new FumlClassifier<org.modeldriven.fuml.repository.Classifier>(this.element.getType());
  }

  @Override
  public Class_ getClass_() {
    return new FumlClass_(this.getDelegate().getClass_());
  }

}
