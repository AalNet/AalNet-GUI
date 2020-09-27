package aalnet.gui.nta

import dk.aau.cs.gui.Tab
import pipe.gui.*
import java.awt.Component
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.io.File
import javax.swing.JButton
import javax.swing.JSplitPane

class NTATab : Tab {

    init {

    }


    override fun getNetChanged(): Boolean {
        return false
    }

    override fun updateEnabledActions(mode: GuiFrame.GUIMode?) {
        //TODO("Not yet implemented")
    }

    override fun setApp(app: GuiFrameActions?) {
        //TODO("Not yet implemented")
    }

    override fun stepForward() {
        //TODO("Not yet implemented")
    }

    override fun mergeNetComponents() {
        //TODO("Not yet implemented")
    }

    override fun changeTimeFeature(isTime: Boolean) {
        //TODO("Not yet implemented")
    }

    override fun importTrace() {
        //TODO("Not yet implemented")
    }

    override fun deleteSelection() {
        //TODO("Not yet implemented")
    }

    override fun decreaseSpacing() {
        //TODO("Not yet implemented")
    }

    override fun showQueries(showDelayEnabledTransitions: Boolean) {
        //TODO("Not yet implemented")
    }

    override fun stepBackwards() {
        //TODO("Not yet implemented")
    }

    override fun toggleAnimationMode() {
        //TODO("Not yet implemented")
    }

    override fun saveNet(outFile: File?) {
        //TODO("Not yet implemented")
    }

    override fun repaintAll() {
        //TODO("Not yet implemented")
    }

    override fun exportTrace() {
        //TODO("Not yet implemented")
    }

    override fun zoomOut() {
        //TODO("Not yet implemented")
    }

    override fun selectAll() {
        //TODO("Not yet implemented")
    }

    override fun showEnabledTransitionsList(showDelayEnabledTransitions: Boolean) {
        //TODO("Not yet implemented")
    }

    override fun zoomIn() {
        //TODO("Not yet implemented")
    }

    override fun verifySelectedQuery() {
        //TODO("Not yet implemented")
    }

    override fun setResizeingDefault() {
        //TODO("Not yet implemented")
    }

    override fun setSafeGuiFrameActions(ref: SafeGuiFrameActions?) {
        //TODO("Not yet implemented")
    }

    override fun getTabTitle(): String {
        return "NTA"
    }

    override fun showComponents(showDelayEnabledTransitions: Boolean) {
        //TODO("Not yet implemented")
    }

    override fun workflowAnalyse() {
        //TODO("Not yet implemented")
    }

    override fun getFile(): File {
        TODO("Not yet implemented")
    }

    override fun zoomTo(newZoomLevel: Int) {
        //TODO("Not yet implemented")
    }

    override fun setMode(mode: Pipe.ElementType?) {
        //TODO("Not yet implemented")
    }

    override fun increaseSpacing() {
        //TODO("Not yet implemented")
    }

    override fun importSUMOQueries() {
        //TODO("Not yet implemented")
    }

    override fun alignPNObjectsToGrid() {
        //TODO("Not yet implemented")
    }

    override fun showDelayEnabledTransitions(showDelayEnabledTransitions: Boolean) {
        //TODO("Not yet implemented")
    }

    override fun showStatistics() {
        //TODO("Not yet implemented")
    }

    override fun changeGameFeature(isGame: Boolean) {
        //TODO("Not yet implemented")
    }

    override fun redo() {
        //TODO("Not yet implemented")
    }

    override fun undo() {
        //TODO("Not yet implemented")
    }

    override fun previousComponent() {
        //TODO("Not yet implemented")
    }

    override fun showConstantsPanel(showDelayEnabledTransitions: Boolean) {
        //TODO("Not yet implemented")
    }

    override fun importXMLQueries() {
        //TODO("Not yet implemented")
    }

    override fun showSharedPT(showSharedPT: Boolean) {
        //TODO("Not yet implemented")
    }

    override fun nextComponent() {
        //TODO("Not yet implemented")
    }

    override fun setGuiFrameControllerActions(guiFrameControllerActions: GuiFrameControllerActions?) {
        //TODO("Not yet implemented")
    }

    val component = NTATabComponent()
    override fun getTabComponent(): Component {
        return component
    }
}

class NTATabComponent : JSplitPane() {
    init {
        layout = GridBagLayout()
        //add(JButton("hi"))
    }
}