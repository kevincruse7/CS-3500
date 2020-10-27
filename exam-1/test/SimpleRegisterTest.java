import org.junit.Test;
import register.ICashRegister;
import register.InsufficientCashException;
import register.SimpleRegister;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;

/**
 * Tester for the SimpleRegister
 */
public final class SimpleRegisterTest
{

    @Test
    public void testOne()
    {
        ICashRegister reg = new SimpleRegister();
        reg.addPennies(4);
        reg.addNickels(4);
        reg.addDimes(5);
        reg.addQuarters(2);
        reg.addOnes(2);
        reg.addFives(5);
        reg.addTens(4);

        Map<Integer,Integer> input = new TreeMap<Integer,Integer>();
        input.put(1,4);
        input.put(5,4);
        input.put(10,5);
        input.put(25,2);
        input.put(100,2);
        input.put(500,5);
        input.put(1000,4);

        Map<Integer,Integer> output = reg.getContents();
        for (Map.Entry<Integer,Integer> s:input.entrySet())
        {
            assertTrue((output.containsKey(s.getKey())) && (output.get(s.getKey()).equals(s.getValue())));
        }
    }

    @Test
    public void testTwo() throws InsufficientCashException
    {
        ICashRegister reg1 = new SimpleRegister();
        ICashRegister reg2 = new SimpleRegister();

        reg1.addDimes(10);
        reg1.addOnes(5);
        reg1.addNickels(5);

        reg2.addQuarters(10);
        Map<Integer,Integer> change1 = reg1.makeChange(2,50);
        Map<Integer,Integer> change2 = reg2.makeChange(2,50);

        int value1=0;
        for (Map.Entry<Integer,Integer> s:change1.entrySet())
        {
            value1 += (s.getKey()*s.getValue());
        }

        int value2=0;
        for (Map.Entry<Integer,Integer> s:change2.entrySet())
        {
            value2 += (s.getKey()*s.getValue());
        }
        assertEquals(value1,value2);
    }

    @Test
    public void testThree()
    {
        ICashRegister reg = new SimpleRegister();
        reg.addDimes(5);
        reg.addOnes(5);

        Map<Integer,Integer> expectedOutput = new TreeMap<Integer,Integer>();
        expectedOutput.put(10,3);
        expectedOutput.put(100,2);
        expectedOutput.put(500,1);

        Map<Integer,Integer> cash;

        try {
            cash = reg.makeChange(7,30);
            fail("some message");
        }
        catch (InsufficientCashException e) {
            reg.addFives(5);
            cash = reg.makeChange(7,30);
            for (Map.Entry<Integer,Integer> entry:cash.entrySet()) {
                assertEquals(expectedOutput.getOrDefault(entry.getKey(),0),entry.getValue());
            }

        }
    }

    @Test(expected = InsufficientCashException.class)
    public void mutateRetrievedContents() {
        ICashRegister reg = new SimpleRegister();
        Map<Integer, Integer> moneyBox = reg.getContents();
        moneyBox.put(1000, 5);
        reg.makeChange(10, 0);
    }
}