package com.example.util;

import org.hibernate.Session;
import org.hibernate.stat.Statistics;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Générateur de rapport de métriques d'exécution et d'analyse des performances JPA / Hibernate.
 */
public class PerformanceReport {

    public static class ScenarioResult {
        public String nomScenario;
        public long dureeMs;
        public long entityLoadCount;
        public long entityInsertCount;
        public long cacheHitCount;
        public long cacheMissCount;
        public long cachePutCount;
        public long queryCount;

        public ScenarioResult(String nomScenario) {
            this.nomScenario = nomScenario;
        }
    }

    private final EntityManager em;
    private final List<ScenarioResult> scenarios = new ArrayList<>();

    public PerformanceReport(EntityManager em) {
        this.em = em;
    }

    public void enregistrerScenario(ScenarioResult result) {
        scenarios.add(result);
    }

    public String genererRapportTexte() {
        Session session = em.unwrap(Session.class);
        Statistics stats = session.getSessionFactory().getStatistics();

        StringBuilder sb = new StringBuilder();
        sb.append("====================================================================\n");
        sb.append("                  RAPPORT GLOBAL DE PERFORMANCE JPA/HIBERNATE       \n");
        sb.append("====================================================================\n");
        sb.append(String.format("Entités insérées             : %d%n", stats.getEntityInsertCount()));
        sb.append(String.format("Entités chargées             : %d%n", stats.getEntityLoadCount()));
        sb.append(String.format("Second Level Cache Hits      : %d%n", stats.getSecondLevelCacheHitCount()));
        sb.append(String.format("Second Level Cache Misses    : %d%n", stats.getSecondLevelCacheMissCount()));
        sb.append(String.format("Second Level Cache Puts      : %d%n", stats.getSecondLevelCachePutCount()));
        sb.append(String.format("Requêtes JPQL/SQL exécutées  : %d%n", stats.getQueryExecutionCount()));
        sb.append(String.format("Temps max d'une requête (ms) : %d ms%n", stats.getQueryExecutionMaxTime()));
        sb.append(String.format("Transactions réussies        : %d%n", stats.getSuccessfulTransactionCount()));
        sb.append("--------------------------------------------------------------------\n");
        sb.append("RECOMMANDATIONS D'OPTIMISATION :\n");

        boolean recommendationFound = false;
        for (ScenarioResult r : scenarios) {
            if (r.cacheHitCount < r.cacheMissCount && r.queryCount > 5) {
                sb.append(String.format(" -> [Scénario '%s'] Une recommandation d’optimisation du cache : taux de hit faible (%d hits vs %d misses) avec un volume de requêtes élevé (%d). Envisager l'activation du Query Cache ou la révision de l'éviction.%n",
                        r.nomScenario, r.cacheHitCount, r.cacheMissCount, r.queryCount));
                recommendationFound = true;
            }
        }

        if (!recommendationFound) {
            sb.append(" -> Aucune anomalie détectée : le cache L2 et les requêtes présentent des performances satisfaisantes.\n");
        }
        sb.append("====================================================================\n");

        return sb.toString();
    }

    public void printReport() {
        System.out.println(genererRapportTexte());
    }
}
