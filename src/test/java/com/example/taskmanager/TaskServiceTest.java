package com.example.taskmanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    private TaskService service;

    @BeforeEach
    void setUp() {
        service = new TaskService();
    }

    @Test
    void createAddsTask() {
        Task t = service.create("Apprendre Jenkins");
        assertNotNull(t.getId());
        assertEquals("Apprendre Jenkins", t.getTitle());
        assertFalse(t.isDone());
        assertEquals(1, service.findAll().size());
    }

    @Test
    void createRejectsBlankTitle() {
        assertThrows(IllegalArgumentException.class, () -> service.create("  "));
    }

    @Test
    void updateMarksTaskAsDone() {
        Task t = service.create("Ecrire le Dockerfile");
        Task updated = service.update(t.getId(), "Ecrire le Dockerfile", true).orElseThrow();
        assertTrue(updated.isDone());
    }

    @Test
    void updateUnknownIdReturnsEmpty() {
        assertTrue(service.update(99L, "x", true).isEmpty());
    }

    @Test
    void deleteRemovesTask() {
        Task t = service.create("Deployer sur Minikube");
        assertTrue(service.delete(t.getId()));
        assertTrue(service.findById(t.getId()).isEmpty());
    }

    @Test
    void deleteUnknownIdReturnsFalse() {
        assertFalse(service.delete(42L));
    }
}
