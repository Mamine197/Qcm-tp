package metier;

import dao.DaoImpl;
import dao.DaoImpl2;
import dao.IDao;
import org.junit.Assert;
import org.junit.Test;

public class MetierImplTest {

    @Test
    public void testCalculAvecMock() {
        // Implémentation bouchon (Mock) pour isoler le test unitaire
        IDao mockDao = new IDao() {
            @Override
            public double getValue() {
                return 10.0;
            }
        };

        MetierImpl metier = new MetierImpl();
        metier.setDao(mockDao);

        double result = metier.calcul();
        Assert.assertEquals(20.0, result, 0.001);
    }

    @Test
    public void testCalculAvecDaoImpl() {
        DaoImpl dao = new DaoImpl();
        MetierImpl metier = new MetierImpl(dao);

        double result = metier.calcul();
        Assert.assertEquals(200.0, result, 0.001);
    }

    @Test
    public void testCalculAvecDaoImpl2() {
        DaoImpl2 dao2 = new DaoImpl2();
        MetierImpl metier = new MetierImpl(dao2);

        double result = metier.calcul();
        Assert.assertEquals(300.0, result, 0.001);
    }

    @Test(expected = IllegalStateException.class)
    public void testCalculSansInjectionDoitEchouer() {
        MetierImpl metier = new MetierImpl();
        metier.calcul();
    }
}
