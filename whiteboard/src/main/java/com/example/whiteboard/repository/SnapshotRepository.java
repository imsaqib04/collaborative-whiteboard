package com.example.whiteboard.repository;

import com.example.whiteboard.model.RoomSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SnapshotRepository extends JpaRepository<RoomSnapshot, Long> {
}