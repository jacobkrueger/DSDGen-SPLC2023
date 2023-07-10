package ui.view;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.WindowConstants;

import org.apache.commons.lang3.StringUtils;

import app.Settings;
import ui.handler.UpdateHandler;
import ui.handler.search.ClassSearchHandler;
import ui.handler.search.ForkSearchHandler;
import ui.handler.search.RepoSearchHandler;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * UI test class
 *
 * @author Alex Mikulinski
 * @since 28.03.2019
 */
public class MainFrame extends JFrame {
	private static final long		serialVersionUID	= -5438098327997188037L;

	private final static String	REPO_SEARCH			= "RepoSearch";
	private final static String	CLASS_SEARCH		= "ClassSearch";
	private final static String	UPDATE				= "Update";
	private final static String	FORK_SEARCH			= "ForkSearch";

	private NavigationPanel			navigationPanel;
	private UpdatePanel				updatePanel;
	private StatusPanel				statusPanel;
	private SearchPanel				repoSearchPanel;
	private SearchPanel				forkSearchPanel;
	private SearchPanel				classSearchPanel;
	private CardLayout				cardLayout;
	private JPanel						mainPanel;
	private JPanel						cardsPanel;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		this.setTitle("DSDGen");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 1081, 618);

		this.setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource("resources/Icon.png")).getImage());

		this._createMainPanel();
		this.setContentPane(this.mainPanel);

		this.navigationPanel	= this._createNavigationPanel();
		this.statusPanel		= new StatusPanel();

		this.updatePanel		= new UpdatePanel();
		this.updatePanel.setUpdateHandler(new UpdateHandler(this.updatePanel));

		this.repoSearchPanel = new SearchPanel();
		this.repoSearchPanel.setWelcomeText("Welcome to DSDGen! Please choose the repository you want to examine.");
		this.repoSearchPanel.setSearchHandler(new RepoSearchHandler(this.repoSearchPanel));

		this._createForkSearchPanel();

		this._createClassSearchPanel();

		this.navigationPanel.setBounds(860, 388, 177, 134);
		this.statusPanel.setBounds(845, 0, 220, 358);

		this.cardLayout = new CardLayout();
		this._createCardsPanel(this.cardLayout);

		this.mainPanel.add(this.cardsPanel);
		this.mainPanel.add(this.navigationPanel);
		this.mainPanel.add(this.statusPanel);
	}

	private void _createClassSearchPanel() {
		ClassSearchHandler	classSearchHandler;
		JButton					btnOpen;

		this.classSearchPanel = new SearchPanel();
		this.classSearchPanel.getBtnSelection().setText("Generate doc");
		this.classSearchPanel.setWelcomeText("Please choose the class you want to examine.");

		btnOpen = new JButton("Open");
		btnOpen.setFont(new Font("Arial", Font.PLAIN, 12));
		btnOpen.setBounds(175, 350, 111, 23);
		this.classSearchPanel.add(btnOpen);

		classSearchHandler = new ClassSearchHandler(this.classSearchPanel);
		btnOpen.addActionListener(e -> classSearchHandler.open());
		this.classSearchPanel.setSearchHandler(classSearchHandler);
	}

	private void _createForkSearchPanel() {
		ForkSearchHandler	forkSearchHandler;
		JButton				btnDownload;

		this.forkSearchPanel = new SearchPanel();
		this.forkSearchPanel.getResultList().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		btnDownload = new JButton("Download");
		btnDownload.setFont(new Font("Arial", Font.PLAIN, 12));
		btnDownload.setBounds(175, 350, 111, 23);
		this.forkSearchPanel.add(btnDownload);
		this.forkSearchPanel.setWelcomeText("Please choose the forks you want to examine.");
		this.forkSearchPanel.getTxtSearchBar().setText("Type in owner name to search for.");

		forkSearchHandler = new ForkSearchHandler(this.forkSearchPanel);
		btnDownload.addActionListener(e -> forkSearchHandler.download());
		this.forkSearchPanel.setSearchHandler(forkSearchHandler);

		this.forkSearchPanel.getResultList().addListSelectionListener(e -> {
			// Prevents this listener from fireing multiple times
			if (e.getValueIsAdjusting()) {
				return;
			}

			@SuppressWarnings("unchecked")
			JList<String> jList = (JList<String>) e.getSource();
			forkSearchHandler
					.setSelectedListItems(Arrays.stream(jList.getSelectedIndices()).boxed().collect(Collectors.toList()));

			forkSearchHandler.showInfo();
		});
	}

	private void _createMainPanel() {
		this.mainPanel = new JPanel();

		this.mainPanel.setBackground(Color.WHITE);
		this.mainPanel.setBounds(100, 100, 1081, 654);
		this.mainPanel.setLayout(null);
	}

	private void _createCardsPanel(CardLayout _cardLayout) {
		this.cardsPanel = new JPanel(this.cardLayout);

		this.cardsPanel.setBounds(0, 0, 843, 575);
		this.cardsPanel.add(this.repoSearchPanel, REPO_SEARCH);
		this.cardsPanel.add(this.forkSearchPanel, FORK_SEARCH);
		this.cardsPanel.add(this.updatePanel, UPDATE);
		this.cardsPanel.add(this.classSearchPanel, CLASS_SEARCH);
	}

	private NavigationPanel _createNavigationPanel() {
		this.navigationPanel = new NavigationPanel();

		this.navigationPanel.getBtnRepoSearch().addActionListener(a -> {
			this.cardLayout.show(this.cardsPanel, REPO_SEARCH);
		});

		this.navigationPanel.getBtnForkSearch().addActionListener(a -> {
			this.cardLayout.show(this.cardsPanel, FORK_SEARCH);
		});

		this.navigationPanel.getBtnClassSearch().addActionListener(a -> {
			this.cardLayout.show(this.cardsPanel, CLASS_SEARCH);
		});

		this.navigationPanel.getBtnUpdate().addActionListener(a -> {
			if (StringUtils.isBlank(Settings.EPROJECT_OWNER.get()) || StringUtils.isBlank(Settings.EPROJECT_NAME.get())) {
				new WarningDialog("Please select a repository before updating!");
				return;
			}
			this.cardLayout.show(this.cardsPanel, UPDATE);
		});

		return this.navigationPanel;
	}
}
