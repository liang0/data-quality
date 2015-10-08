package org.talend.dataquality.statistics;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.talend.dataquality.statistics.cardinality.CardinalityAnalyzer;
import org.talend.dataquality.statistics.cardinality.CardinalityStatistics;
import org.talend.dataquality.statistics.frequency.FrequencyStatistics;
import org.talend.datascience.common.inference.Analyzer;
import org.talend.datascience.common.inference.Analyzers;
import org.talend.datascience.common.inference.Analyzers.Result;
import org.talend.datascience.common.inference.type.DataTypeAnalyzer;

public class AnalyzersTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAnalyze() {
        Analyzer<Result> analyzer = Analyzers.with(new CardinalityAnalyzer(), new DataTypeAnalyzer());
        String[] data = new String[] { "0", "1", "2", "3", "16", "17", "18", "19", "19" };
        for (String r : data) {
            analyzer.analyze(r);
        }
        Assert.assertEquals(8, analyzer.getResult().get(0).get(CardinalityStatistics.class).getDistinctCount(), 0);
        Assert.assertEquals(1, analyzer.getResult().get(0).get(CardinalityStatistics.class).getDuplicateCount(), 0);
        Assert.assertFalse(analyzer.getResult().get(0).exist(FrequencyStatistics.class));
    }
}