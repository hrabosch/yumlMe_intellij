package org.hrabosch.yumlme.intelli.plugin.editor;

import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.EventDispatcher;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Implementation of {@link FileEditor} interface which
 * generate custom UI {@link org.hrabosch.yumlme.intelli.plugin.editor.ui.YumlmeEditorUI}.
 *
 * @author hrabosch
 */
public class YumlmeFileEditorImpl implements FileEditor {

	private static final String NAME = "YumlmeEditor";

	private final YumlmeEditor yumlmeEditor;
	private final EventDispatcher<PropertyChangeListener> myDispatcher = EventDispatcher.create(PropertyChangeListener.class);


	public YumlmeFileEditorImpl(@NotNull Project project, @NotNull VirtualFile file) {
		yumlmeEditor = new YumlmeEditorImpl(project, file);
		Disposer.register(this, yumlmeEditor);
	}

	@NotNull
	@Override
	public JComponent getComponent() {
		return yumlmeEditor.getJComponent();
	}

	@Nullable
	@Override
	public JComponent getPreferredFocusedComponent() {
		return yumlmeEditor.getContentComponent();
	}

	@NotNull
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public void setState(@NotNull FileEditorState fileEditorState) {

	}

	@Override
	public boolean isModified() {
		return false;
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public void selectNotify() {

	}

	@Override
	public void deselectNotify() {

	}

	@Override
	public void addPropertyChangeListener(@NotNull PropertyChangeListener propertyChangeListener) {
		myDispatcher.addListener(propertyChangeListener);
	}

	@Override
	public void removePropertyChangeListener(@NotNull PropertyChangeListener propertyChangeListener) {
		myDispatcher.removeListener(propertyChangeListener);
	}

	@Nullable
	@Override
	public BackgroundEditorHighlighter getBackgroundHighlighter() {
		return null;
	}

	@Nullable
	@Override
	public FileEditorLocation getCurrentLocation() {
		return null;
	}

	@Override
	public void dispose() {

	}

	@Nullable
	@Override
	public <T> T getUserData(@NotNull Key<T> key) {
		return null;
	}

	@Override
	public <T> void putUserData(@NotNull Key<T> key, @Nullable T t) {

	}
}
