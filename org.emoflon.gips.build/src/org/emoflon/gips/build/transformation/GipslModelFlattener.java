package org.emoflon.gips.build.transformation;

import java.util.Collection;
import java.util.stream.Collectors;

import org.emoflon.gips.gipsl.gipsl.EditorFile;
import org.emoflon.gips.gipsl.gipsl.GipsMapping;
import org.emoflon.gips.gipsl.gipsl.GipslFactory;
import org.emoflon.gips.gipsl.gipsl.GipslPackage;
import org.emoflon.gips.gipsl.util.GipslResourceManager;
import org.emoflon.ibex.gt.gtl.util.GTLModelFlattener;

public class GipslModelFlattener extends GTLModelFlattener {

	final protected GipslFactory gipslFactory = GipslPackage.eINSTANCE.getGipslFactory();

//	public GipslModelFlattener(final EditorFile file, boolean loadCompletePackage) throws Exception {
//		this(new GipslResourceManager(), file, loadCompletePackage);
//	}
//
//	public GipslModelFlattener(final GipslResourceManager gtlManager, final EditorFile file,
//			boolean loadCompletePackage) throws Exception {
//		super(gtlManager, (loadCompletePackage) ? loadAllFilesInPkg(gtlManager, file) : List.of(file));
//	}
//
//	public GipslModelFlattener(final Collection<EditorFile> files) throws Exception {
//		this(new GipslResourceManager(), files);
//	}
//	
//	public static Collection<org.emoflon.ibex.gt.gtl.gTL.EditorFile> loadAllFilesInPkg(
//			final GipslResourceManager gtlManager, final EditorFile file) {
//		Collection<org.emoflon.ibex.gt.gtl.gTL.EditorFile> files = gtlManager.loadAllOtherEditorFilesInPackage(file);
//		files.add(file);
//		return files;
//	}

	public GipslModelFlattener(final GipslResourceManager gtlManager, final Collection<EditorFile> files)
			throws Exception {
		super(gtlManager,
				files.stream().map(file -> (org.emoflon.ibex.gt.gtl.gTL.EditorFile) file).collect(Collectors.toList()));

		files.stream().filter(file -> (file instanceof EditorFile)).map(file -> file)
				.forEach(file -> flattenGipslModel(file));
	}

	@Override
	protected EditorFile createNewEditorFile() {
		EditorFile file = gipslFactory.createEditorFile();
		return file;
	}

	@Override
	public EditorFile getFlattenedModel() {
		return (EditorFile) flattenedFile;
	}

	protected void flattenGipslModel(final EditorFile file) {
		EditorFile flattenedFile = getFlattenedModel();
		// Flatten mappings
		for (GipsMapping mapping : file.getMappings()) {
			GipsMapping flattenedMapping = flatten(mapping);
			flattenedFile.getMappings().add(flattenedMapping);
		}

	}

	protected GipsMapping flatten(final GipsMapping mapping) {
		GipsMapping flattenedMapping = gipslFactory.createGipsMapping();
		flattenedMapping.setName(mapping.getName());
		flattenedMapping.setPattern(name2rule.get(mapping.getPattern().getName()));
		return flattenedMapping;
	}
}
