package com.mishmash.alpha.presentation;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;

import com.google.common.collect.Maps;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;

import com.mishmash.alpha.ModifierOperation;
import com.mishmash.alpha.VehicleType;
import com.mishmash.alpha.presentation.VehicleGuiPart;
import java.awt.Toolkit;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;

public class VehicleBuilderApp {

    private JFrame frmVehicleCalculator;
    private final GuiController controller = new GuiController();
    private JComboBox frameComboBox;
    private JComboBox frontWheelComboBox;
    private JComboBox rearWheelComboBox;
    private JComboBox powerPlantComboBox;
    private JComboBox riderComboBox;
    private JLabel outputLabel;
    private final Map<JComboBox, VehicleGuiPart> selectorMap = Maps.newHashMap();
    private JComboBox typeComboBox;
    private JButton calculateButton;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VehicleBuilderApp window = new VehicleBuilderApp();
                    window.frmVehicleCalculator.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public VehicleBuilderApp() {
        initialize();
        collectAndAddListenersToComboBoxes();
        updatePartComboBoxContents((VehicleType)this.typeComboBox.getSelectedItem());
        checkDataAndUpdateValues();
        
    }
    
    /**
     * Read the initial data.
     */
    private void checkDataAndUpdateValues() {
        for (JComboBox box : selectorMap.keySet()) {
            String selected = (String)box.getSelectedItem();
            if (selected != null) {
                controller.updateMap(selectorMap.get(box), selected);
            }
            calculateButton.setEnabled(allBoxesHaveSelection());
        }
    }

    /**
     * Initialize the contents of the frame. Method constructed by WindowPro.
     */
    private void initialize() {
        frmVehicleCalculator = new JFrame();
        frmVehicleCalculator.setIconImage(Toolkit.getDefaultToolkit().getImage(VehicleBuilderApp.class.getResource("/com/mishmash/alpha/resources/MLogo.png")));
        frmVehicleCalculator.setTitle("Vehicle Calculator");
        frmVehicleCalculator.setBounds(100, 100, 450, 300);
        frmVehicleCalculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmVehicleCalculator.getContentPane().setLayout(new BorderLayout(0, 0));
        
        JPanel panel = new JPanel();
        panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        frmVehicleCalculator.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new BorderLayout(0, 0));
        
        JSplitPane vehicleTypeSplitPane = new JSplitPane();
        panel.add(vehicleTypeSplitPane, BorderLayout.NORTH);
        
        JLabel lblSelectVehicleType = new JLabel("Select Vehicle Type");
        vehicleTypeSplitPane.setLeftComponent(lblSelectVehicleType);
        
        typeComboBox = new JComboBox();
        
        typeComboBox.setModel(new DefaultComboBoxModel(VehicleType.validValues()));
        typeComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final JComboBox cb = (JComboBox)e.getSource();
                final VehicleType typeSelected = (VehicleType)cb.getSelectedItem();
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        updatePartComboBoxContents(typeSelected);
                        checkDataAndUpdateValues();
                    }
                });
            }
        });
        
        vehicleTypeSplitPane.setRightComponent(typeComboBox);
        
        JPanel mainPanel = new JPanel();
        panel.add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new GridLayout(5, 2, 0, 0));
        
        JLabel lblFrame = new JLabel("Frame:");
        mainPanel.add(lblFrame);
        
        frameComboBox = new JComboBox();
        mainPanel.add(frameComboBox);
        
        JLabel lblFrontWheel = new JLabel("Front Wheel:");
        mainPanel.add(lblFrontWheel);
        
        frontWheelComboBox = new JComboBox();
        mainPanel.add(frontWheelComboBox);
        
        JLabel lblRearWheel = new JLabel("Rear Wheel:");
        mainPanel.add(lblRearWheel);
        
        rearWheelComboBox = new JComboBox();
        mainPanel.add(rearWheelComboBox);
        
        JLabel lblPowerPlant = new JLabel("Power Plant:");
        mainPanel.add(lblPowerPlant);
        
        powerPlantComboBox = new JComboBox();
        mainPanel.add(powerPlantComboBox);
        
        JLabel lblRider = new JLabel("Rider:");
        mainPanel.add(lblRider);
        
        riderComboBox = new JComboBox();
        mainPanel.add(riderComboBox);
        
        JSplitPane outputSplitPane = new JSplitPane();
        frmVehicleCalculator.getContentPane().add(outputSplitPane, BorderLayout.SOUTH);
        
        outputLabel = new JLabel("");
        outputSplitPane.setRightComponent(outputLabel);
        
        calculateButton = new JButton("Calculate Distance");
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        updateOutputLabel();
                    }
                });
            }
        });
        outputSplitPane.setLeftComponent(calculateButton);
        
        JMenuBar menuBar = new JMenuBar();
        menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        frmVehicleCalculator.getContentPane().add(menuBar, BorderLayout.NORTH);
        
        JMenu optionsMenu = new JMenu("Options");
        menuBar.add(optionsMenu);
        
        JMenuItem operationMenuItem = new JMenuItem("Edit Modifier Stacking");
        operationMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        setOperation();
                    }
                });
            }
        });
        optionsMenu.add(operationMenuItem);
        
        
    }
    
    /**
     * Updates the stringlists to which the comboboxes are bound, based
     * on the type of vehicle.
     * 
     * @param type
     * The type of vehicle selected.
     */
    private void updatePartComboBoxContents(VehicleType type) {
        for (JComboBox box : selectorMap.keySet()) {
            updateSingleComboBox(box, controller.getPartsForType(type, selectorMap.get(box)));
        }
    }
    
    /**
     * Changes the operation used for stacking modifications
     */
    private void setOperation() {
        Object selected = JOptionPane.showInputDialog(null, "Select stacking operation", 
                "Stacking Operation", 
                JOptionPane.QUESTION_MESSAGE, null, 
                ModifierOperation.valueStrings(), controller.getModiferStackingOperation().toString());
        if (selected != null && selected instanceof String) {
            controller.setModifierStackingOperation(ModifierOperation.fromString((String) selected));
        }
        
    }
    
    /**
     * Convenience method for updating an indivicual combobox.
     * 
     * @param box
     * ComboBox to update.
     * 
     * @param newContents
     * The new string list to which it is bound.
     */
    private void updateSingleComboBox(JComboBox box, List<String> newContents) {
        DefaultComboBoxModel boxModel = new DefaultComboBoxModel(newContents.toArray());
        String selection  = (String)box.getSelectedItem();
        box.setModel(boxModel);
        if (selection != null && newContents.contains(selection)) {
            box.setSelectedItem(selection);
        } else {
            box.setSelectedIndex(-1);
            outputLabel.setText("");
            calculateButton.setEnabled(false);
        }
    }
    
    /**
     * Puts the distance traveled in the output box.
     */
    private void updateOutputLabel() {
        if (allBoxesHaveSelection()) {
            outputLabel.setText(controller.getOutPutText());
        }
    }
    
    /**
     * Checks whether or not all the comboboxes have selections. Boxes get cleared
     * if an invalid member is selected when the vehicle type is changed.
     * 
     * @return
     * True if all comboboxes have a selection, false otherwise.
     */
    private boolean allBoxesHaveSelection() {
        return (frameComboBox.getSelectedIndex() != -1) &&
                (frontWheelComboBox.getSelectedIndex() != -1) &&
                (rearWheelComboBox.getSelectedIndex() != -1) &&
                (powerPlantComboBox.getSelectedIndex() != -1) &&
                (riderComboBox.getSelectedIndex() != -1);
    }
    
    /**
     * Adds all comboboxes to the map for easy access and 
     * adds action listeners to them.
     */
    private void collectAndAddListenersToComboBoxes() {
        selectorMap.put(frameComboBox, VehicleGuiPart.FRAME);
        selectorMap.put(frontWheelComboBox, VehicleGuiPart.FRONT_WHEEL);
        selectorMap.put(rearWheelComboBox, VehicleGuiPart.REAR_WHEEL);
        selectorMap.put(powerPlantComboBox, VehicleGuiPart.POWER_PLANT);
        selectorMap.put(riderComboBox, VehicleGuiPart.RIDER);
        
        for (JComboBox box : selectorMap.keySet()) {
            box.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    final JComboBox fcb = (JComboBox)e.getSource();
                    final String itemSelected = (String)fcb.getSelectedItem();
                    EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            controller.updateMap(selectorMap.get(fcb), itemSelected);
                            calculateButton.setEnabled(allBoxesHaveSelection());
                        }
                    });
                }
            });
        }
    }
   

}
