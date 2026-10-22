package metier;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import presentation.Presentation2;

public class SpringContextTest {

    @Test
    public void testAnnotationDrivenContext() {
        // Validation du conteneur IoC configuré par annotations Java
        ApplicationContext context = new AnnotationConfigApplicationContext(Presentation2.class);
        Assert.assertNotNull("Le contexte Spring par annotations doit être initialisé", context);

        IMetier metier = context.getBean(IMetier.class);
        Assert.assertNotNull("Le bean IMetier doit être présent dans le conteneur", metier);

        // Par défaut MetierImpl a @Qualifier("dao2") (150.0 * 2 = 300.0)
        double result = metier.calcul();
        Assert.assertEquals(300.0, result, 0.001);
    }

    @Test
    public void testXmlApplicationContext() {
        // Validation du conteneur IoC configuré par fichier XML
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Assert.assertNotNull("Le contexte Spring XML doit être initialisé", context);

        IMetier metier = (IMetier) context.getBean("metier");
        Assert.assertNotNull("Le bean metier doit être instancié depuis le XML", metier);

        double result = metier.calcul();
        Assert.assertEquals(300.0, result, 0.001);
    }
}
