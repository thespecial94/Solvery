package ru.magomed.solverycourse.mapper;

public interface DtoToEntityMapper<D, E> {
    E toEntity(D dto);
    D toDto(E entity);
}