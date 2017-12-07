package org.hrabosch.yumlme.intelli.plugin.editor;

import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileEditor.FileEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * Custom editor provider implementation of {@link FileEditorProvider} to start custom editor
 * {@link YumlmeFileEditorImpl}.
 *
 * @author hrabosch
 */
public class YumlmeEditorProvider implements FileEditorProvider {

	@NonNls
	private static final String EXTENSION = "yumlme";
	@NonNls
	private static final String EDITOR_TYPE_ID = "yumlmeDiagrams";

	@Override
	public boolean accept(@NotNull Project project, @NotNull VirtualFile virtualFile) {
		return virtualFile.getExtension().equalsIgnoreCase(EXTENSION);
	}

	@NotNull
	@Override
	public FileEditor createEditor(@NotNull Project project, @NotNull VirtualFile virtualFile) {
		return new YumlmeFileEditorImpl(project, virtualFile);
	}

	@NotNull
	@Override
	public String getEditorTypeId() {
		return EDITOR_TYPE_ID;
	}

	@NotNull
	@Override
	public FileEditorPolicy getPolicy() {
		return FileEditorPolicy.PLACE_AFTER_DEFAULT_EDITOR;
	}
}
