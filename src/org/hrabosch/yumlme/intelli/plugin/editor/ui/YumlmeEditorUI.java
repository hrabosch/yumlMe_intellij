package org.hrabosch.yumlme.intelli.plugin.editor.ui;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.util.ui.UIUtil;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.hrabosch.yumlme.intelli.plugin.editor.YumlmeEditor;
import org.hrabosch.yumlme.intelli.plugin.editor.YumlmeEditorImpl;
import org.hrabosch.yumlme.intelli.plugin.helper.YumlmeConverterUtil;

/**
 * UI definition.
 *
 * @author hrabosch
 */
public class YumlmeEditorUI extends JPanel {

	private YumlmeEditor editor;

	private final JPanel contentPanel;
	private final JPanel imagePanel;
	private final JScrollPane scrollPane;
	private final JLabel imageLabel;
	private final JSlider slider;
	private final JLabel sliderLabel;
	private final JPanel scalePanel;
	private final JButton refreshDiagramButton;

	private double scale = 1.5;

	private BufferedImage diagram;

	public final int SCALE_MAX = 100;
	public int ACTUAL_SCALE = 50;

	public YumlmeEditorUI(YumlmeEditorImpl yumlmeEditor) {
		this.editor = yumlmeEditor;
		this.setLayout(new BorderLayout());
		this.setBackground(Color.WHITE);

		contentPanel = new JPanel(new BorderLayout());
		imagePanel = new JPanel(new BorderLayout());

		contentPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		contentPanel.setBackground(Color.WHITE);

		imageLabel = new JLabel("");
		imagePanel.add(imageLabel, BorderLayout.CENTER);
		contentPanel.add(imageLabel, BorderLayout.CENTER);

		scalePanel = new JPanel(new BorderLayout());
		scalePanel.setBackground(Color.WHITE);

		slider = new JSlider(JSlider.HORIZONTAL, 1, SCALE_MAX, ACTUAL_SCALE);
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateScale();
			}
		});
		slider.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if (e.getWheelRotation() < 0) {
					if (slider.getValue() <= SCALE_MAX - 1) {
						slider.setValue(slider.getValue() + 1);
					}
				} else {
					if (slider.getValue() >= 2) {
						slider.setValue(slider.getValue() - 1);
					}
				}
				updateScale();
			}
		});

		sliderLabel = new JLabel("Diagram scale", SwingConstants.CENTER);

		refreshDiagramButton = new JButton();
		refreshDiagramButton.setBackground(Color.WHITE);
		try {
			Image img = ImageIO
					.read(getClass().getResource("resources/org/hrabosch/yumlme/intelli/plugin/refresh.png"));
			refreshDiagramButton.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			refreshDiagramButton.setText("Refresh");
		}

		refreshDiagramButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				diagram = loadDiagram();
				updateScale();
			}
		});

		scalePanel.add(refreshDiagramButton, BorderLayout.WEST);
		scalePanel.add(sliderLabel, BorderLayout.CENTER);
		scalePanel.add(slider, BorderLayout.AFTER_LAST_LINE);
		this.add(scalePanel, BorderLayout.BEFORE_FIRST_LINE);

		scrollPane = new JBScrollPane(contentPanel);
		this.add(scrollPane);
		diagram = loadDiagram();
		updateScale();
	}

	private void updateView() {
		if (diagram != null) {
			int imageWidth = diagram.getWidth();
			int imageHeight = diagram.getHeight();
			BufferedImage bi = UIUtil.createImage(
					(int) (imageWidth * scale),
					(int) (imageHeight * scale),
					diagram.getType());
			Graphics2D g2 = bi.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			AffineTransform at = AffineTransform.getTranslateInstance(0, 0);
			at.scale(scale, scale);
			g2.drawRenderedImage(diagram, at);
			imageLabel.setIcon(new ImageIcon(bi));
		}
	}

	private BufferedImage loadDiagram() {
		Document document = FileDocumentManager.getInstance().getDocument(editor.getFile());

		return YumlmeConverterUtil.generateDiagram(document.getText());
	}

	public JComponent getDiagramComponent() {
		return contentPanel;
	}

	private void updateScale() {
		int value = this.slider.getValue();
		scale = value / 100.0;
		updateView();
	}
}
