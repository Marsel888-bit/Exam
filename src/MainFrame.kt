import painting.ExceptionPainter
import ui.GraphicsPanel
import ui.painting.CartesianPainter
import ui.painting.FunctionPainter
import ui.painting.ImplicitPlotPainter
import ui.painting.Painter
import java.awt.Color
import java.awt.Dimension
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.sin

class MainFrame: JFrame() {
    val minDim = Dimension(600, 600)
    val mainPanel: GraphicsPanel
    val controlPanel: JPanel
    val XMin: JLabel
    val XMax: JLabel
    val YMin: JLabel
    val YMax: JLabel
    val subset2: JLabel
    val subset3: JLabel
    val bound_left: JLabel
    val bound_right: JLabel
    val xMin: JSpinner
    val xMax: JSpinner
    val yMin: JSpinner
    val yMax: JSpinner
    val cbGraphic: JCheckBox
    val cbParam: JCheckBox
    val absColorPanel: JPanel
    val paramColorPanel: JPanel
    val xMinM: SpinnerNumberModel
    val xMaxM: SpinnerNumberModel
    val yMinM: SpinnerNumberModel
    val yMaxM: SpinnerNumberModel
    val tMin: JTextField
    val tMax: JTextField

    init {
        minimumSize = minDim
        defaultCloseOperation = EXIT_ON_CLOSE
        xMinM = SpinnerNumberModel(-5.0, -100.0, 4.9, 0.1)
        xMin = JSpinner(xMinM)
        xMaxM = SpinnerNumberModel(5.0, -4.9, 100.0, 0.1)
        xMax = JSpinner(xMaxM)
        yMinM = SpinnerNumberModel(-5.0, -100.0, 4.9, 0.1)
        yMin = JSpinner(yMinM)
        yMaxM = SpinnerNumberModel(5.0, -4.9, 100.0, 0.1)
        yMax = JSpinner(yMaxM)
        tMin = JTextField("-1.0")
        tMax = JTextField("1.0")
        cbGraphic = JCheckBox()
        cbParam = JCheckBox()
        absColorPanel = JPanel()
        absColorPanel.background = Color.BLUE
        absColorPanel.setSize(1, 1)
        paramColorPanel = JPanel()
        paramColorPanel.background = Color.GREEN
        absColorPanel.setSize(1, 1)

        controlPanel = JPanel().apply { background = Color.WHITE }


        val plane = Plane(
            xMin.value as Double,
            xMax.value as Double,
            yMin.value as Double,
            yMax.value as Double
        )

        val cartesianPainter = CartesianPainter(plane)

        var t1 = tMin.getText().toDouble()
        var t2 = tMax.getText().toDouble()

        val parametricPainter = ImplicitPlotPainter(plane, t1, t2)
        parametricPainter.funColor = paramColorPanel.background
        parametricPainter.functionX = { t: Double -> exp(t) * sin(t) }
        parametricPainter.functionY = { t: Double -> exp(t) * cos(t) }


        val f = FunctionPainter(plane)
        f.function = { x: Double -> abs(x + 1) + abs(x - 2) }
        f.funColor = absColorPanel.background

        val exception = ExceptionPainter(plane)
        val painters = mutableListOf<Painter>(cartesianPainter)
        mainPanel = GraphicsPanel(painters).apply {
            background = Color.WHITE
        }

        cbGraphic.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                if (cbGraphic.isSelected) {
                    painters.add(f)
                } else {
                    painters.remove(f)
                }
                mainPanel.repaint()
            }
        })

        cbParam.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {

                if (cbParam.isSelected) {
                    painters.add(parametricPainter)
                } else {
                    painters.remove(parametricPainter)
                }
                mainPanel.repaint()
            }
        })

        absColorPanel.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                if (e?.button == 1) {
                    val color = JColorChooser.showDialog(null, "???????????????? ???????? ??????????????", absColorPanel.background)
                    absColorPanel.background = color
                    f.funColor = color
                    mainPanel.repaint()
                }

            }
        })
         tMin.addActionListener() {
             try {
                 parametricPainter.t_min = tMin.text.toDouble()
             }
             catch (e: Exception) {
                 painters.add(exception)
             }
            mainPanel.repaint()
        }

         tMax.addActionListener() {
             try {
                 parametricPainter.t_max = tMax.text.toDouble()
             }
             catch (e: Exception) {
                 painters.add(exception)
             }
            mainPanel.repaint()
        }

        paramColorPanel.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                if (e?.button == 1) {
                    val color = JColorChooser.showDialog(null, "???????????????? ???????? ??????????????", paramColorPanel.background)
                    paramColorPanel.background = color
                    parametricPainter.funColor = color
                    mainPanel.repaint()
                }

            }
        })

        mainPanel.addComponentListener(object : ComponentAdapter() {
            override fun componentResized(e: ComponentEvent?) {
                plane.width = mainPanel.width
                plane.height = mainPanel.height
                mainPanel.repaint()
            }

        })

        xMin.addChangeListener {
            xMaxM.minimum = xMin.value as Double + 0.1
            plane.xSegment = Pair(xMin.value as Double, xMax.value as Double)
            mainPanel.repaint()
        }
        xMax.addChangeListener {
            xMinM.maximum = xMax.value as Double - 0.1
            plane.xSegment = Pair(xMin.value as Double, xMax.value as Double)
            mainPanel.repaint()
        }
        yMin.addChangeListener {
            yMaxM.minimum = yMin.value as Double + 0.1
            plane.ySegment = Pair(yMin.value as Double, yMax.value as Double)
            mainPanel.repaint()
        }
        yMax.addChangeListener {
            yMinM.maximum = yMax.value as Double - 0.1
            plane.ySegment = Pair(yMin.value as Double, yMax.value as Double)
            mainPanel.repaint()
        }

        XMin = JLabel("XMin:")
        XMax = JLabel("XMax:")
        YMin = JLabel("YMin:")
        YMax = JLabel("YMax:")
        subset2 = JLabel("???????????????????? ???????????? ??????????????, ???????????????? ????????")
        subset3 = JLabel("???????????????????? ???????????? ??????????????, ???????????????? ????????????????????????????")
        bound_left = JLabel("t_min")
        bound_right = JLabel("t_max")

        controlPanel.layout = GroupLayout(controlPanel).apply {
            linkSize(XMin, xMin)
            linkSize(XMax, xMax)
            linkSize(YMin, yMin)
            linkSize(YMax, yMax)
            linkSize(absColorPanel, paramColorPanel, cbGraphic, cbParam)
            linkSize(tMax, tMin)


            setHorizontalGroup(
                createSequentialGroup()
                    .addGap(10)
                    .addGroup(createParallelGroup().addComponent(XMin).addComponent(YMin))
                    .addGroup(
                        createParallelGroup().addComponent(
                            xMin,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE
                        ).addComponent(
                            yMin,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE
                        )
                    )
                    .addGap(10, 20, 20)
                    .addGroup(createParallelGroup().addComponent(XMax).addComponent(YMax))
                    .addGroup(
                        createParallelGroup().addComponent(
                            xMax,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE
                        ).addComponent(
                            yMax,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE
                        )
                    )
                    .addGap(50)
                    .addGroup(
                        createParallelGroup().addComponent(cbGraphic).addComponent(cbParam)
                    )
                    .addGroup(createParallelGroup().addComponent(subset2).addComponent(subset3))
                    .addGap(10)
                    .addGroup(
                        createParallelGroup().addComponent(absColorPanel)
                            .addComponent(paramColorPanel)
                    )
                    .addGap(10)
                    .addGroup(createParallelGroup().addComponent(tMin).addComponent(tMax))
                    .addGap(10)
                    .addGroup(createParallelGroup().addComponent(bound_left).addComponent(bound_right))
            )
            setVerticalGroup(
                createParallelGroup(GroupLayout.Alignment.CENTER).addGroup(
                    createSequentialGroup().addGroup(
                        createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addComponent(XMin)
                            .addComponent(
                                xMin,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.PREFERRED_SIZE
                            )
                            .addComponent(XMax)
                            .addComponent(
                                xMax,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.PREFERRED_SIZE,
                                GroupLayout.PREFERRED_SIZE
                            )
                    )
                        .addGap(2)
                        .addGroup(
                            createParallelGroup()
                                .addComponent(YMin)
                                .addComponent(
                                    yMin,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(YMax)
                                .addComponent(
                                    yMax,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.PREFERRED_SIZE
                                )
                        )
                ).addGroup(
                    createSequentialGroup()
                        .addGap(2)
                        .addGroup(
                            createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(cbGraphic)
                                .addComponent(subset2)
                                .addComponent(bound_left)
                                .addComponent(absColorPanel)
                                .addComponent(tMin)
                        )
                        .addGap(2)
                        .addGroup(
                            createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(cbParam)
                                .addComponent(subset3)
                                .addComponent(bound_right)
                                .addComponent(paramColorPanel)
                                .addComponent(tMax)
                        )
                )
            )
        }
        layout = GroupLayout(contentPane).apply {
            setAutoCreateGaps(true);
            setAutoCreateContainerGaps(true);
            setVerticalGroup(
                createSequentialGroup()
                    .addComponent(mainPanel)
                    .addComponent(controlPanel)
            )
            setHorizontalGroup(
                createParallelGroup()
                    .addComponent(mainPanel)
                    .addComponent(controlPanel)
            )
        }

        pack()
        plane.width = mainPanel.width
        plane.height = mainPanel.height

    }
}