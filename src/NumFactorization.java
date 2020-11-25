import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class NumFactorization {

    public static final double LOG_2 = Math.log(2.0);
    private static final int MAX_DIGITS_2 = 977;

    private static BigInteger minusOne = BigInteger.valueOf(-1);
    private static BigInteger zero = BigInteger.valueOf(0);
    private static BigInteger one = BigInteger.valueOf(1);
    private static BigInteger two = BigInteger.valueOf(2);
    private static BigInteger five = BigInteger.valueOf(5);

    private BigInteger firstTestValue = BigInteger.valueOf(1359331);
    private BigInteger secondTestValue = BigInteger.valueOf(1728239);

    private BigInteger firstTaskValue = new BigInteger("28477664061664212667");
    private BigInteger secondTaskValue = new BigInteger("698408978153482166117078010002055744013");
    private BigInteger thirdTaskValue = new BigInteger("23651023355036410898741834524339837330713340825191206370016213417092022165851053");

    public NumFactorization()
    {
        long startTime = System.currentTimeMillis();

        //PollardP0Factorization(firstTestValue);
        //PollardP1Factorization(secondTestValue);

        //PollardP0Factorization(firstTaskValue);
        //PollardP1Factorization(firstTaskValue);

        //PollardP0Factorization(secondTaskValue);
        //PollardP1Factorization(secondTaskValue);

        //PollardP0Factorization(thirdTaskValue);
        //PollardP1Factorization(thirdTaskValue);

        System.out.println("Time: " + (double) (System.currentTimeMillis() - startTime)  + "\n");
    }

    public static double logBigInteger(BigInteger val) {
        if (val.signum() < 1)
            return val.signum() < 0 ? Double.NaN : Double.NEGATIVE_INFINITY;
        int blex = val.bitLength() - MAX_DIGITS_2; // any value in 60..1023 works here
        if (blex > 0)
            val = val.shiftRight(blex);
        double res = Math.log(val.doubleValue());
        return blex > 0 ? res + blex * LOG_2 : res;
    }

    public BigInteger FunctionForPollard(BigInteger x, BigInteger n)
    {
        return x.pow(2).add(five).mod(n);
    }

    public BigInteger GetRandomBigInteger(BigInteger maxLimit, BigInteger minLimit) {
        BigInteger bigInteger = maxLimit.subtract(minLimit);
        Random randNum = new Random();
        int len = maxLimit.bitLength();
        BigInteger resultBigInteger = new BigInteger(len, randNum);
        if (resultBigInteger.compareTo(minLimit) < 0)
            resultBigInteger = resultBigInteger.add(minLimit);
        if (resultBigInteger.compareTo(bigInteger) >= 0)
            resultBigInteger = resultBigInteger.mod(bigInteger).add(minLimit);
        return resultBigInteger;
    }

    public static void main(String[] args) {
        NumFactorization numFactorization = new NumFactorization();
    }

    public void PollardP0Factorization(BigInteger n)
    {
        n = n.abs();
        String tmp = " i |  a  |  b  |  d  |";
        String line = "---------------------";

        System.out.println();
        System.out.println(tmp);
        System.out.println(line);

        BigInteger c = GetRandomBigInteger(n.subtract(one), one);
        //BigInteger c = one;
        //1
        BigInteger a = c;
        BigInteger b = c;
        BigInteger d = one;
        BigInteger counter = zero;

        System.out.println(" " + counter + " | " + a + " | " + b + " | " + d + " | ");

        while (d.equals(one)) {

            //2
            a = FunctionForPollard(a, n).mod(n);
            b = FunctionForPollard(b, n).mod(n);
            b = FunctionForPollard(b, n).mod(n);

            //3
            d = a.subtract(b).gcd(n);

            counter = counter.add(one);
            System.out.println(" " + counter + " | " + a + " | " + b + " | " + d + " | ");

            if (d.equals(one)) continue;

            if (d.compareTo(one) == 1 || b.compareTo(n) == -1)
            {
                System.out.println("Result: " + d);
                break;
            }

            if(d.equals(n))
            {
                System.out.println("Divider not found");
                break;
            }
        }

    }

    public void PollardP1Factorization(BigInteger n)
    {
        n = n.abs();
        System.out.println();
        BigInteger p = two;
        BigInteger a = GetRandomBigInteger(n.subtract(two), two);
        BigInteger d = a.gcd(n);
        BigInteger l = one;
        BigInteger counter = zero;

        if (d.compareTo(one) > 0)
        {
            System.out.println("d = " + d);
            return;
        }

        while (true)
        {
            l = BigInteger.valueOf((long) (Math.log(n.doubleValue())/Math.log(p.doubleValue())));
            System.out.println("i = " + counter + "; Base: " + p + "; l_i: " + l);
            counter = counter.add(one);
            a = a.modPow(p.pow(l.intValue()), n);
            d = a.subtract(one).gcd(n);

            if (!d.equals(one) && !d.equals(n))
            {
                System.out.println("Result: " + d);
                return;
            }

            p = p.add(one);
            while (!p.isProbablePrime(p.intValue()))
                p = p.add(one);
            //System.out.println("p = " + p);
        }
    }
}
