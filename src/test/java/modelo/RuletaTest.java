package modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RuletaTest {

    private final Ruleta ruleta = new Ruleta();

    @Test
    void depositar() {
        int monto = 1000;
        int otroMonto = 0;

        boolean deposito = ruleta.depositar(monto);
        boolean deposito2 = ruleta.depositar(otroMonto);

        assertTrue(deposito);
        assertTrue(deposito2);
    }

    @Test
    void getColor() {
    }

    @Test
    void getSaldo() {
    }
}