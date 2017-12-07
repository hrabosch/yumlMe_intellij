package org.hrabosch.yumlme.intelli.plugin.editor;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import javax.swing.JComponent;
import org.hrabosch.yumlme.intelli.plugin.editor.ui.YumlmeEditorUI;

/**
 * Custom editor implementation.
 *
 * @author hrabosch
 */
public class YumlmeEditorImpl implements YumlmeEditor {

	private VirtualFile file;
	private Project project;

	private final YumlmeEditorUI editorUI;

	private boolean disposed;

	public YumlmeEditorImpl(Project project, VirtualFile file) {
		this.file = file;
		this.project = project;

		editorUI = new YumlmeEditorUI(this);
	}

	@Override
	public VirtualFile getFile() {
		return file;
	}


	@Override
	public Project getProject() {
		return project;
	}

	@Override
	public JComponent getJComponent() {
		return editorUI;
	}

	@Override
	public JComponent getContentComponent() {
		return editorUI.getDiagramComponent();
	}

	@Override
	public void dispose() {
		disposed = true;
	}
}
