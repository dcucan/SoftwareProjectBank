package bankband.bank.controllers;

/**
 * Rozhraní pro všechny controllery, které se posílá scenemenegeru a v metodě activate pak spouští
 * znovu initialize
 */
public interface Controller {

     void initialize();
}
