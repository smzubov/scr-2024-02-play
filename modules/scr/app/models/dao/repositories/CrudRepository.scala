package models.dao.repositories

import org.squeryl.dsl.ast.LogicalBoolean
import org.squeryl.{KeyedEntity, PrimitiveTypeMode, Table}

trait CrudRepository[K, Entity <: KeyedEntity[K]] extends PrimitiveTypeMode{

  type Filter = Entity => LogicalBoolean

  def defaultTable: Table[Entity]

  def list(): List[Entity] = transaction(
    from(defaultTable)(e => select(e)).toList
  )
  def list(filters: Filter*): List[Entity] = transaction{
    from(defaultTable)(e => where(combineFilters(filters:_*)(e)) select(e)).toList
  }
  def find(id: K): Option[Entity] = transaction(defaultTable.lookup(id))
  def insert(entity: Entity): Entity = transaction(defaultTable.insert(entity))
  def update(entity: Entity): Unit = transaction(defaultTable.update(entity))
  def delete(entity: Entity): Unit = transaction(defaultTable.delete(entity.id))

  protected def combineFilters(filters: Filter*): Filter =
    e => filters.foldLeft[LogicalBoolean](1 === 1)((acc, f) => acc and f(e))
}
