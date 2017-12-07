package org.hrabosch.yumlme.intelli.plugin.editor;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import javax.swing.JComponent;

/**
 * Editor interface.
 *
 * @author hrabosch
 */
public interface YumlmeEditor extends Disposable{

	VirtualFile getFile();

	Project getProject();

	JComponent getJComponent();

	JComponent getContentComponent();

}
