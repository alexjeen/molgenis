package org.molgenis.data.annotation.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.molgenis.data.Entity;
import org.molgenis.data.annotation.AnnotationService;
import org.molgenis.data.annotation.impl.datastructures.HGNCLocations;
import org.molgenis.data.annotation.provider.HgncLocationsProvider;
import org.molgenis.data.annotation.provider.KeggDataProvider;
import org.molgenis.data.annotation.AbstractAnnotatorTest;
import org.molgenis.data.support.MapEntity;
import org.molgenis.data.vcf.VcfRepository;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class KeggAnnotatorServiceTest extends AbstractAnnotatorTest
{
	@BeforeMethod
	public void beforeMethod() throws IOException
	{
		AnnotationService annotationService = mock(AnnotationService.class);

		entity.set(VcfRepository.CHROM, "2");
		entity.set(VcfRepository.POS, new Long(58453844l));
		
		input.add(entity);

		String keggHsaData = "hsa:55120	FANCL, FAAP43, PHF9, POG; Fanconi anemia, complementation group L; K10606 E3 ubiquitin-protein ligase FANCL [EC:6.3.2.19]";
		String keggPathwayData = "path:hsa03460	Fanconi anemia pathway - Homo sapiens (human)\npath:hsa04120	Ubiquitin mediated proteolysis - Homo sapiens (human)";
		String keggPathwayHsaData = "path:hsa03460	hsa:55120\npath:hsa04120	hsa:55120";
		KeggDataProvider keggDataProvider = mock(KeggDataProvider.class);
		when(keggDataProvider.getKeggHsaReader()).thenReturn(new StringReader(keggHsaData));
		when(keggDataProvider.getKeggPathwayReader()).thenReturn(new StringReader(keggPathwayData));
		when(keggDataProvider.getKeggPathwayHsaReader()).thenReturn(new StringReader(keggPathwayHsaData));

		HgncLocationsProvider hgncLocationsProvider = mock(HgncLocationsProvider.class);
		Map<String, HGNCLocations> locationsMap = Collections.singletonMap("FANCL", new HGNCLocations("FANCL",
				58453844l - 10, 58453844l + 10, "2"));
		when(hgncLocationsProvider.getHgncLocations()).thenReturn(locationsMap);
		annotator = new KeggServiceAnnotator(annotationService, hgncLocationsProvider, keggDataProvider);
	}

	@Test
	public void annotateTest()
	{
		List<Entity> expectedList = new ArrayList<Entity>();
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

		resultMap.put(KeggServiceAnnotator.KEGG_GENE_ID, "hsa:55120");
		resultMap.put(KeggServiceAnnotator.KEGG_PATHWAYS_IDS, "path:hsa03460, path:hsa04120");
		resultMap.put(KeggServiceAnnotator.KEGG_PATHWAYS_NAMES,
				"Fanconi anemia pathway - Homo sapiens (human), Ubiquitin mediated proteolysis - Homo sapiens (human)");

		Entity expectedEntity = new MapEntity(resultMap);

		expectedList.add(expectedEntity);

		Iterator<Entity> results = annotator.annotate(input);

		Entity resultEntity = results.next();

		assertEquals(resultEntity.get(KeggServiceAnnotator.KEGG_GENE_ID),
				expectedEntity.get(KeggServiceAnnotator.KEGG_GENE_ID));
		assertEquals(resultEntity.get(KeggServiceAnnotator.KEGG_PATHWAYS_IDS),
				expectedEntity.get(KeggServiceAnnotator.KEGG_PATHWAYS_IDS));
		assertEquals(resultEntity.get(KeggServiceAnnotator.KEGG_PATHWAYS_NAMES),
				expectedEntity.get(KeggServiceAnnotator.KEGG_PATHWAYS_NAMES));
	}
}