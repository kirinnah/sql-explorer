package org.example.exception;

public class EntityNotFoundException extends DAOException {
    public EntityNotFoundException(String entityName, Object id) {
        super(entityName + " with id=" + id + " not found");
    }
}