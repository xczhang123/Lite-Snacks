/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package assignment2;

import assignment2.backend.*;
import assignment2.model.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CashTest {

    /**
     * Test the get quantity method for cash
     */
    @Test
    void getQuantityTest() {
        assertEquals(7 , CashDB.getQuantity("test1"));
        CashDB.addCash("test1", 2);
        assertEquals(9 , CashDB.getQuantity("test1"));
        CashDB.useCash("test1", 2);
        assertEquals(7 , CashDB.getQuantity("test1"));
    }

    /**
     * Test the get quantity method for cash
     */
    @Test
    void getInvalidQuantityTest() {
        assertEquals(0 , CashDB.getQuantity("mess"));
    }
}
