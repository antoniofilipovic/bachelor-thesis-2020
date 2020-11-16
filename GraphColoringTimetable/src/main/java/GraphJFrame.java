import com.formdev.flatlaf.FlatIntelliJLaf;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import hr.fer.zpm.Simulacija;
import hr.fer.zpm.model.Brid;
import hr.fer.zpm.model.NacinRada;
import hr.fer.zpm.model.Predmet;
import hr.fer.zpm.model.Čvor;
import org.apache.commons.collections15.Transformer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ova klasa sluzi za prikazivanje rezultata
 *
 * @author antonio
 */
public class GraphJFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextArea jTextAreaIspis;
    private JComboBox<Integer> mogucnostiTerminiPoDanu;
    private JComboBox<String> nacinRada;
    private JComboBox<Integer> mogucnostiDanaUkupno;
    private JComponent grafPanel;
    private JTabbedPane tabovi;
    private final Simulacija simulacija;
    private final Map<String, NacinRada> mapaNacinaRada = new HashMap<>();

    public GraphJFrame() {
        super("Raspored termina");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(20, 20);
        setSize(700, 500);
        this.setLocationRelativeTo(null);
        simulacija = new Simulacija();
        mapaNacinaRada.put("METODA PRIRODNOG RAZDVAJANJA", NacinRada.PRIRODNO_RASPOREDJIVANJE);
        mapaNacinaRada.put("METODA MINIMALNI PRESJEK", NacinRada.MIN_METODA);
        initGUI();
    }

    public static Color hex2Rgb(String colorStr) {
        return new Color(Integer.valueOf(colorStr.substring(0, 2), 16), Integer.valueOf(colorStr.substring(2, 4), 16),
                Integer.valueOf(colorStr.substring(4, 6), 16));
    }

    /**
     * This method starts when main program stars
     *
     * @param args
     */
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatIntelliJLaf());
                // SubstanceCortex.GlobalScope.setSkin(new ModerateSkin());
            } catch (Exception e) {
                System.out.println("Substance Graphite failed to initialize");
            }
            GraphJFrame w = new GraphJFrame();
            w.setVisible(true);
        });
    }

    /**
     * This method initializes gui
     */
    private void initGUI() {
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());
        stvoriOpcijePanel(cp);
        stvoriPanelSIzbornicima(cp);
        stvoriGumb(cp);
        this.setIconImage(new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR));

    }

    private void stvoriGumb(Container cp) {
        JButton pocniSimulacijuGumb = new JButton("Počni simulaciju");
        pocniSimulacijuGumb.addMouseListener(noviMouseAdapter());

        pocniSimulacijuGumb.setOpaque(true);
        pocniSimulacijuGumb.setBackground(Color.white);
        JPanel gumbPanel = new JPanel();
        gumbPanel.add(pocniSimulacijuGumb);
        gumbPanel.setBackground(hex2Rgb("99b3e6"));
        gumbPanel.setOpaque(true);
        cp.add(gumbPanel, BorderLayout.SOUTH);

    }

    private void stvoriOpcijePanel(Container cp) {
        JPanel glavniPanel = new JPanel();
        glavniPanel.setLayout(new BorderLayout());
        glavniPanel.setBackground(hex2Rgb("99b3e6"));

        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new GridLayout(1, 2));
        sliderPanel.setBackground(hex2Rgb("99b3e6"));

        // termina po danu
        mogucnostiTerminiPoDanu = new JComboBox<Integer>(new Integer[]{1, 2, 3, 4, 5});

        mogucnostiTerminiPoDanu.setSelectedItem(3);

        JLabel labelaTerminaPoDanu = new JLabel();
        labelaTerminaPoDanu.setText("Termina po danu");
        JPanel panelTerminaPoDanu = new JPanel();
        panelTerminaPoDanu.add(labelaTerminaPoDanu);
        panelTerminaPoDanu.add(mogucnostiTerminiPoDanu);

        panelTerminaPoDanu.setBackground(hex2Rgb("99b3e6"));
        sliderPanel.add(panelTerminaPoDanu);

        // dana ukupno

        mogucnostiDanaUkupno = new JComboBox<Integer>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        mogucnostiDanaUkupno.setSelectedItem(9);

        JLabel labelaBrojDana = new JLabel();
        labelaBrojDana.setText("Broj dana");

        JPanel panelBrojDana = new JPanel();
        panelBrojDana.add(labelaBrojDana);
        panelBrojDana.add(mogucnostiDanaUkupno);

        panelBrojDana.setBackground(hex2Rgb("99b3e6"));
        sliderPanel.add(panelBrojDana);


        //nacin rada

        nacinRada = new JComboBox<String>(new String[]{"METODA PRIRODNOG RAZDVAJANJA", "METODA MINIMALNI PRESJEK"});
        //nacinRada.setSelectedItem(0);

        JLabel labelaNacinRada = new JLabel();
        labelaNacinRada.setText("Način rada");

        JPanel panelNacinRada = new JPanel();
        panelNacinRada.add(labelaNacinRada);
        panelNacinRada.add(nacinRada);

        panelNacinRada.setBackground(hex2Rgb("99b3e6"));
        sliderPanel.add(panelNacinRada);

        glavniPanel.add(sliderPanel, BorderLayout.NORTH);

        cp.add(glavniPanel, BorderLayout.NORTH);

    }

    private void stvoriPanelSIzbornicima(Container cp) {
        tabovi = new JTabbedPane();

        JComponent tekstPanel = napraviTekstPanel();
        tabovi.addTab("Tekst panel", tekstPanel);

        tabovi.addTab("Graf prije bojanja", new JLabel("A"));
        cp.add(tabovi, BorderLayout.CENTER);

        tabovi.addTab("Obojani graf", new JLabel("B"));
        cp.add(tabovi, BorderLayout.CENTER);

    }

    private JComponent napraviGraf(List<Čvor<Predmet>> čvorovi, List<Brid<Predmet>> bridovi, boolean boja,
                                   int edgeType) {
        Graph<String, Integer> graf = new SparseGraph<String, Integer>();
        Map<String, Color> mapaBoja = new HashMap<>();

        for (Čvor<Predmet> cvor : čvorovi) {
            graf.addVertex(cvor.getIme());
            mapaBoja.put(cvor.getIme(), cvor.getBoja());
        }

        if (edgeType == 1) {
            Predmet p = bridovi.get(0).getPrviČvor().getStruktura();
            for (Brid<Predmet> b : bridovi) {
                if (b.getPrviČvor().getStruktura().equals(p)) {
                    graf.addEdge(b.getID(), b.getPrviČvor().getIme(), b.getDrugiČvor().getIme());
                }
            }
        }
        if (edgeType == 2) {
            Predmet p = bridovi.get(0).getPrviČvor().getStruktura();
            for (Brid<Predmet> b : bridovi) {
                if (b.getPrviČvor().getStruktura().equals(p)) {
                    graf.addEdge(b.getID(), b.getPrviČvor().getIme(), b.getDrugiČvor().getIme());
                }
            }
        }

        // Layout<V, E>, VisualizationComponent<V,E>
        Layout<String, Integer> layout = new CircleLayout<String, Integer>(graf);

        layout.setSize(new Dimension(300, 300));
        VisualizationViewer<String, Integer> vizualizacija = new VisualizationViewer<String, Integer>(layout);
        vizualizacija.setGraphLayout(layout);
        vizualizacija.setPreferredSize(new Dimension(350, 350));

        Transformer<String, Paint> cvoroviBoja = new Transformer<String, Paint>() {
            public Paint transform(String predmet) {
                if (boja) {
                    return mapaBoja.get(predmet);
                }
                return Color.LIGHT_GRAY;

            }
        };

        float[] linija = {10.0f};
        final Stroke bridoviBoja = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, linija,
                0.0f);
        Transformer<Integer, Stroke> bridoviBojaTransformator = new Transformer<Integer, Stroke>() {
            public Stroke transform(Integer s) {
                return bridoviBoja;
            }
        };

        vizualizacija.getRenderContext().setVertexFillPaintTransformer(cvoroviBoja);
        vizualizacija.getRenderContext().setEdgeStrokeTransformer(bridoviBojaTransformator);
        vizualizacija.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vizualizacija.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        vizualizacija.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);

        DefaultModalGraphMouse<Integer, String> model = new DefaultModalGraphMouse<>();

        model.setMode(ModalGraphMouse.Mode.PICKING);
        vizualizacija.setGraphMouse(model);
        vizualizacija.setBackground(Color.WHITE);
        return vizualizacija;
    }

    private JComponent napraviTekstPanel() {
        jTextAreaIspis = new JTextArea();
        jTextAreaIspis.setBackground(Color.WHITE);
        jTextAreaIspis.setEditable(true); // set textArea non-editable
        jTextAreaIspis.setForeground(Color.BLACK);

        Font font = jTextAreaIspis.getFont();
        float size = font.getSize() + 1.0f;
        jTextAreaIspis.setFont(font.deriveFont(size));

        //jTextAreaIspis.setFont(jTextAreaIspis.getFont().deriveFont(Font.BOLD, jTextAreaIspis.getFont().getSize()));

        JScrollPane skrolPanel = new JScrollPane(jTextAreaIspis);
        skrolPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jTextAreaIspis.setText("Odaberi vrijednosti i pokreni simulaciju.");

        JPanel panelTekst = new JPanel();
        panelTekst.setLayout(new BorderLayout());
        panelTekst.add(skrolPanel, BorderLayout.CENTER);
        return panelTekst;
    }

    private MouseAdapter noviMouseAdapter() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int brojDana = (Integer) mogucnostiDanaUkupno.getSelectedItem();
                int brojTermina = (Integer) mogucnostiTerminiPoDanu.getSelectedItem();
                simulacija.setBROJ_DANA(brojDana);
                simulacija.setBROJ_TERMINA_PO_DANU(brojTermina);

                StringBuilder tekst = new StringBuilder();
                tekst.append("Odabrani broj dana je:" + brojDana + "\n");
                tekst.append("Odabrani broj termina je:" + brojTermina + "\n");
                tekst.append("------------------------------------------------------\n");


                try {
                    simulacija.pripremiNovuSimulaciju();
                    tekst.append("Rješenje postoji.\n");
                    tekst.append("------------------------------------------------------\n");
                    tekst.append(simulacija.pocniSimulaciju(mapaNacinaRada.get(nacinRada.getSelectedItem())));

                    tabovi.setComponentAt(2, napraviGraf(simulacija.getČvorovi(), simulacija.getBridovi(), true, 1));
                    tabovi.setComponentAt(1, napraviGraf(simulacija.getČvorovi(), simulacija.getBridovi(), false, 1));

                } catch (Exception e1) {
                    tekst.append("RJEŠENJE NE POSTOJI. PROBAJTE DRUGAČIJE POSTAVITI PARAMETRE." + e1);
                    e1.printStackTrace();

                }
                jTextAreaIspis.setText(tekst.toString());

            }

        };
    }

}
