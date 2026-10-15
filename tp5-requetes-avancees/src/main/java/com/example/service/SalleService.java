package com.example.service;

import com.example.model.Reservation;
import com.example.model.Salle;
import com.example.util.PaginationResult;
import java.time.LocalDateTime;
import java.util.List;

public interface SalleService {
    void creerSalle(Salle salle);
    void creerReservation(Reservation reservation);
    List<Salle> getAvailableRooms(LocalDateTime debut, LocalDateTime fin);
    List<Salle> searchRooms(String nom, Integer capaciteMin, LocalDateTime debut, LocalDateTime fin, int page, int size);
    PaginationResult<Salle> searchRoomsWithPagination(String nom, Integer capaciteMin, LocalDateTime debut, LocalDateTime fin, int page, int size);
}