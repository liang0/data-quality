// ============================================================================
//
// Copyright (C) 2006-2019 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataquality.record.linkage.grouping.swoosh;

import org.junit.Assert;
import org.junit.Test;
import org.talend.dataquality.matchmerge.Attribute;
import org.talend.dataquality.matchmerge.AttributeValues;
import org.talend.dataquality.matchmerge.Record;
import org.talend.dataquality.record.linkage.grouping.swoosh.SurvivorShipAlgorithmParams.SurvivorshipFunction;
import org.talend.dataquality.record.linkage.utils.SurvivorShipAlgorithmEnum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DQMFBRecordMergerTest {

    /**
     * Test method for {@link DQMFBRecordMerger#createNewRecord(Record, Record, long)} .
     * 
     * @throws ParseException
     */
    @Test
    public void testMerge1() throws ParseException {
        DQMFBRecordMerger dqMFBRecordMerger = new DQMFBRecordMerger(null, null,
                new SurvivorShipAlgorithmEnum[] { SurvivorShipAlgorithmEnum.MOST_RECENT },
                initSurvivorShipAlgorithmParams(SurvivorShipAlgorithmEnum.MOST_RECENT, 2));
        Map<String, String> patternMap = new HashMap<>();
        String datePattern = "dd-MM-yyyy"; //$NON-NLS-1$
        patternMap.put("0", datePattern); //$NON-NLS-1$
        patternMap.put("1", datePattern); //$NON-NLS-1$
        dqMFBRecordMerger.setColumnDatePatternMap(patternMap);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        List<Attribute> r1Arributes = new ArrayList<>();
        List<Attribute> r2Arributes = new ArrayList<>();
        String referenceValue = "03-03-2003"; //$NON-NLS-1$
        String inputColValue = "02-02-2000"; //$NON-NLS-1$
        String colName = "HIREDATE"; //$NON-NLS-1$
        Attribute attribute1 = new Attribute(colName, 0, inputColValue, 1);
        attribute1.setReferenceValue(referenceValue);
        r1Arributes.add(attribute1);
        referenceValue = "05-05-2005"; //$NON-NLS-1$
        inputColValue = "06-06-2006"; //$NON-NLS-1$
        Attribute attribute2 = new Attribute(colName, 0, inputColValue, 1);
        attribute2.setReferenceValue(referenceValue);
        r2Arributes.add(attribute2);
        RichRecord record1 =
                new RichRecord(r1Arributes, "record1", simpleDateFormat.parse("02-02-2000").getTime(), "MFB"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        RichRecord record2 =
                new RichRecord(r2Arributes, "record2", simpleDateFormat.parse("03-03-3000").getTime(), "MFB"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        record1.setOriginRow(initDQAttribute("02-02-2000", "03-03-2003", "beijing")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        record2.setOriginRow(initDQAttribute("06-06-2006", "05-05-2005", "shanghai")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        RichRecord createNewRecord = (RichRecord) dqMFBRecordMerger.createNewRecord(record1, record2, 0);
        Assert.assertEquals("Merge value should be shanghai", "shanghai", //$NON-NLS-1$//$NON-NLS-2$
                createNewRecord.getOriginRow().get(2).getValue());
        Assert.assertEquals("Merge reference value should be 05-05-2005", "05-05-2005", //$NON-NLS-1$//$NON-NLS-2$
                createNewRecord.getOriginRow().get(2).getReferenceValue());
        Assert.assertEquals("Merge reference column index value should be 1", 1, //$NON-NLS-1$
                createNewRecord.getOriginRow().get(2).getReferenceColumnIndex());
    }

    /**
     * Test method for {@link DQMFBRecordMerger#createNewRecord(Record, Record, long)} .
     * 
     * @throws ParseException
     * case 2 for datePattern is null
     */
    @Test
    public void testMerge2() throws ParseException {
        DQMFBRecordMerger dqMFBRecordMerger = new DQMFBRecordMerger(null, null,
                new SurvivorShipAlgorithmEnum[] { SurvivorShipAlgorithmEnum.MOST_RECENT },
                initSurvivorShipAlgorithmParams(SurvivorShipAlgorithmEnum.MOST_RECENT, 2));
        Map<String, String> patternMap = new HashMap<>();
        String datePattern = "dd-MM-yyyy"; //$NON-NLS-1$
        patternMap.put("0", datePattern); //$NON-NLS-1$
        patternMap.put("1", datePattern); //$NON-NLS-1$
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        List<Attribute> r1Arributes = new ArrayList<>();
        List<Attribute> r2Arributes = new ArrayList<>();
        String referenceValue = "03-03-2003"; //$NON-NLS-1$
        String inputColValue = "02-02-2000"; //$NON-NLS-1$
        String colName = "HIREDATE"; //$NON-NLS-1$
        Attribute attribute1 = new Attribute(colName, 0, inputColValue, 1);
        attribute1.setReferenceValue(referenceValue);
        r1Arributes.add(attribute1);
        referenceValue = "05-05-2005"; //$NON-NLS-1$
        inputColValue = "06-06-2006"; //$NON-NLS-1$
        Attribute attribute2 = new Attribute(colName, 0, inputColValue, 1);
        attribute2.setReferenceValue(referenceValue);
        r2Arributes.add(attribute2);
        RichRecord record1 =
                new RichRecord(r1Arributes, "record1", simpleDateFormat.parse("02-02-2000").getTime(), "MFB"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        RichRecord record2 =
                new RichRecord(r2Arributes, "record2", simpleDateFormat.parse("03-03-3000").getTime(), "MFB"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        record1.setOriginRow(initDQAttribute("02-02-2000", "03-03-2003", "beijing")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        record2.setOriginRow(initDQAttribute("06-06-2006", "05-05-2005", "shanghai")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        RichRecord createNewRecord = (RichRecord) dqMFBRecordMerger.createNewRecord(record1, record2, 0);
        // compare timestamp in this case
        Assert.assertEquals("Merge value should be shanghai", "shanghai", //$NON-NLS-1$//$NON-NLS-2$
                createNewRecord.getOriginRow().get(2).getValue());
        Assert.assertEquals("Merge reference value should be 05-05-2005", "05-05-2005", //$NON-NLS-1$//$NON-NLS-2$
                createNewRecord.getOriginRow().get(2).getReferenceValue());
        Assert.assertEquals("Merge reference column index value should be 1", 1, //$NON-NLS-1$
                createNewRecord.getOriginRow().get(2).getReferenceColumnIndex());
    }

    /**
     * Test method for {@link DQMFBRecordMerger#createNewRecord(Record, Record, long)} .
     * 
     * @throws ParseException
     * case 2 for datePattern is not null but the pattern is null case
     */
    @Test
    public void testMerge3() throws ParseException {
        DQMFBRecordMerger dqMFBRecordMerger = new DQMFBRecordMerger(null, null,
                new SurvivorShipAlgorithmEnum[] { SurvivorShipAlgorithmEnum.MOST_RECENT },
                initSurvivorShipAlgorithmParams(SurvivorShipAlgorithmEnum.MOST_RECENT, 2));
        Map<String, String> patternMap = new HashMap<>();
        String datePattern = "dd-MM-yyyy"; //$NON-NLS-1$
        patternMap.put("0", datePattern); //$NON-NLS-1$
        dqMFBRecordMerger.setColumnDatePatternMap(patternMap);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        List<Attribute> r1Arributes = new ArrayList<>();
        List<Attribute> r2Arributes = new ArrayList<>();
        String referenceValue = "03-03-2003"; //$NON-NLS-1$
        String inputColValue = "02-02-2000"; //$NON-NLS-1$
        String colName = "HIREDATE"; //$NON-NLS-1$
        Attribute attribute1 = new Attribute(colName, 0, inputColValue, 1);
        attribute1.setReferenceValue(referenceValue);
        r1Arributes.add(attribute1);
        referenceValue = "05-05-2005"; //$NON-NLS-1$
        inputColValue = "06-06-2006"; //$NON-NLS-1$
        Attribute attribute2 = new Attribute(colName, 0, inputColValue, 1);
        attribute2.setReferenceValue(referenceValue);
        r2Arributes.add(attribute2);
        RichRecord record1 =
                new RichRecord(r1Arributes, "record1", simpleDateFormat.parse("02-02-2000").getTime(), "MFB"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        RichRecord record2 =
                new RichRecord(r2Arributes, "record2", simpleDateFormat.parse("03-03-3000").getTime(), "MFB"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        record1.setOriginRow(initDQAttribute("02-02-2000", "03-03-2003", "beijing")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        record2.setOriginRow(initDQAttribute("06-06-2006", "05-05-2005", "shanghai")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        RichRecord createNewRecord = (RichRecord) dqMFBRecordMerger.createNewRecord(record1, record2, 0);
        // compare timestamp in this case
        Assert.assertEquals("Merge value should be shanghai", "shanghai", //$NON-NLS-1$//$NON-NLS-2$
                createNewRecord.getOriginRow().get(2).getValue());
        Assert.assertEquals("Merge reference value should be shanghai", "shanghai", //$NON-NLS-1$//$NON-NLS-2$
                createNewRecord.getOriginRow().get(2).getReferenceValue());
        Assert.assertEquals("Merge reference column index value should be 1", 1, //$NON-NLS-1$
                createNewRecord.getOriginRow().get(2).getReferenceColumnIndex());
    }

    /**
     * TDQ-18347 : (1,'AAA','add') - merged from ('A','A'，'A'）
     *             (7，‘A','add2')
     * should be merged : (1,'AAA,A','add') - merged from ('AAA','A'）, but not ("AAA,A','A')
     */
    @Test
    public void testMerge_TDQ18347() throws ParseException {
        String[] funcParams = new String[3];
        funcParams[0] = "Dummy";
        funcParams[0] = "Concatenate";
        funcParams[2] = "Dummy";
        DQMFBRecordMerger dqMFBRecordMerger = new DQMFBRecordMerger("MFB", funcParams,
                new SurvivorShipAlgorithmEnum[] { SurvivorShipAlgorithmEnum.CONCATENATE,
                        SurvivorShipAlgorithmEnum.CONCATENATE, SurvivorShipAlgorithmEnum.CONCATENATE },
                initSurvivorShipAlgorithmParams(SurvivorShipAlgorithmEnum.CONCATENATE, 1));

        // init record
        RichRecord record1 = new RichRecord(initAttribute("1", "AAA", "beijing"), "1", 0, "MFB"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        RichRecord record2 = new RichRecord(initAttribute("7", "A", "shanghai"), "7", 0, "MFB"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        record1.setOriginRow(initDQAttribute_18347("1", "AAA", "TJ")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        record2.setOriginRow(initDQAttribute_18347("7", "A", "SH")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

        AttributeValues<String> leftValues = record1.getAttributes().get(1).getValues();
        leftValues.get("A").increment();
        leftValues.get("A").increment();
        leftValues.get("A").increment();

        RichRecord createNewRecord = (RichRecord) dqMFBRecordMerger.merge(record1, record2);

        Assert.assertEquals("Merge value should be AAAA", "AAAA", //$NON-NLS-1$//$NON-NLS-2$
                createNewRecord.getAttributes().get(1).getValue());
        Assert.assertEquals(5, createNewRecord.getAttributes().get(1).getValues().size());
        Iterator<String> iterator = createNewRecord.getAttributes().get(1).getValues().iterator();
        Assert.assertEquals("A", iterator.next());
        Assert.assertEquals("AAA", iterator.next());

        createNewRecord = (RichRecord) dqMFBRecordMerger.merge(record2, record1);

        Assert.assertEquals("Merge value should be AAAA", "AAAA", //$NON-NLS-1$//$NON-NLS-2$
                createNewRecord.getAttributes().get(1).getValue());
        iterator = createNewRecord.getAttributes().get(1).getValues().iterator();
        Assert.assertEquals("A", iterator.next());
        Assert.assertEquals("AAA", iterator.next());
    }

    private List<DQAttribute<?>> initDQAttribute_18347(String col0, String col1, String col2) {
        List<DQAttribute<?>> attributeList = new ArrayList<>();
        attributeList.add(new DQAttribute<Object>("ID", 0, col0)); //$NON-NLS-1$
        attributeList.add(new DQAttribute<Object>("COUNTRY", 1, col1)); //$NON-NLS-1$
        attributeList.add(new DQAttribute<Object>("ADDRESS", 2, col2, 1)); //$NON-NLS-1$
        return attributeList;
    }

    private List<Attribute> initAttribute(String c1, String c2, String c3) {
        List<Attribute> attributeList = new ArrayList<>();
        attributeList.add(new Attribute("ID", 0, c1)); //$NON-NLS-1$
        attributeList.add(new Attribute("COUNTRY", 1, c2)); //$NON-NLS-1$
        attributeList.add(new Attribute("ADDRESS", 2, c3)); //$NON-NLS-1$
        return attributeList;
    }

    private SurvivorShipAlgorithmParams initSurvivorShipAlgorithmParams(SurvivorShipAlgorithmEnum algorithm,
            int index) {
        SurvivorShipAlgorithmParams surShipAlgoriPar = new SurvivorShipAlgorithmParams();
        Map<Integer, SurvivorshipFunction> defaultSurviorshipRules = new HashMap<>();
        SurvivorshipFunction survivFunction = surShipAlgoriPar.new SurvivorshipFunction();
        survivFunction.setSurvivorShipAlgoEnum(algorithm);
        survivFunction.setReferenceColumnIndex(1);
        defaultSurviorshipRules.put(index, survivFunction);
        surShipAlgoriPar.setDefaultSurviorshipRules(defaultSurviorshipRules);
        return surShipAlgoriPar;
    }

    private List<DQAttribute<?>> initDQAttribute(String col0, String col1, String col2) {
        List<DQAttribute<?>> attributeList = new ArrayList<>();
        attributeList.add(new DQAttribute<Object>("HIREDATE", 0, col0)); //$NON-NLS-1$
        attributeList.add(new DQAttribute<Object>("ENDDATE", 1, col1)); //$NON-NLS-1$
        attributeList.add(new DQAttribute<Object>("CITY", 2, col2, 1)); //$NON-NLS-1$
        return attributeList;
    }

}
