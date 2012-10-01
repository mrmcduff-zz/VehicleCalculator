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
import com.mishmash.alpha.VehicleType;
import com.mishmash.alpha.presentation.VehicleGuiPart;

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
        selectorMap.put(frameComboBox, VehicleGuiPart.FRAME);
        selectorMap.put(frontWheelComboBox, VehicleGuiPart.FRONT_WHEEL);
        selectorMap.put(rearWheelComboBox, VehicleGuiPart.REAR_WHEEL);
        selectorMap.put(powerPlantComboBox, VehicleGuiPart.POWER_PLANT);
        selectorMap.put(riderComboBox, VehicleGuiPart.RIDER);
        addListenersToComboBoxes();
        updatePartComboBoxContents((VehicleType)this.typeComboBox.getSelectedItem());
        checkDataAndUpdateValues();
        
    }
    
    private void checkDataAndUpdateValues() {
     // read the data initially
        for (JComboBox box : selectorMap.keySet()) {
            String selected = (String)box.getSelectedItem();
            if (selected != null) {
                controller.updateMap(selectorMap.get(box), selected);
            }
        }
        updateOutputLabel();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmVehicleCalculator = new JFrame();
        frmVehicleCalculator.setTitle("Vehicle Calculator");
        frmVehicleCalculator.setBounds(100, 100, 450, 300);
        frmVehicleCalculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmVehicleCalculator.getContentPane().setLayout(new BorderLayout(0, 0));
        
        JPanel panel = new JPanel();
        panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        frmVehicleCalculator.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(5, 2, 0, 0));
        
        JLabel lblFrame = new JLabel("Frame:");
        panel.add(lblFrame);
        
        frameComboBox = new JComboBox();
        panel.add(frameComboBox);
        
        JLabel lblFrontWheel = new JLabel("Front Wheel:");
        panel.add(lblFrontWheel);
        
        frontWheelComboBox = new JComboBox();
        panel.add(frontWheelComboBox);
        
        JLabel lblRearWheel = new JLabel("Rear Wheel:");
        panel.add(lblRearWheel);
        
        rearWheelComboBox = new JComboBox();
        panel.add(rearWheelComboBox);
        
        JLabel lblPowerPlant = new JLabel("Power Plant:");
        panel.add(lblPowerPlant);
        
        powerPlantComboBox = new JComboBox();
        panel.add(powerPlantComboBox);
        
        JLabel lblRider = new JLabel("Rider:");
        panel.add(lblRider);
        
        riderComboBox = new JComboBox();
        panel.add(riderComboBox);
        
        JSplitPane outputSplitPane = new JSplitPane();
        frmVehicleCalculator.getContentPane().add(outputSplitPane, BorderLayout.SOUTH);
        
        JLabel lblTotalDistanceFor = new JLabel("Total Distance for Vehicle:");
        outputSplitPane.setLeftComponent(lblTotalDistanceFor);
        
        outputLabel = new JLabel("");
        outputSplitPane.setRightComponent(outputLabel);
        
        JSplitPane vehicleTypeSplitPane = new JSplitPane();
        frmVehicleCalculator.getContentPane().add(vehicleTypeSplitPane, BorderLayout.NORTH);
        
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
        
        
    }
    
    private void updatePartComboBoxContents(VehicleType type) {
        for (JComboBox box : selectorMap.keySet()) {
            updateSingleComboBox(box, controller.getPartsForType(type, selectorMap.get(box)));
        }
    }
    
    private void updateSingleComboBox(JComboBox cb, List<String> newContents) {
        DefaultComboBoxModel boxModel = new DefaultComboBoxModel(newContents.toArray());
        String selection  = (String)cb.getSelectedItem();
        cb.setModel(boxModel);
        if (selection != null && newContents.contains(selection)) {
            cb.setSelectedItem(selection);
        }
    }
    
    private void updateOutputLabel() {
        if (allBoxesHaveSelection()) {
            outputLabel.setText(controller.getOutPutText());
        }
    }
    
    private boolean allBoxesHaveSelection() {
        return (frameComboBox.getSelectedIndex() != -1) &&
                (frontWheelComboBox.getSelectedIndex() != -1) &&
                (rearWheelComboBox.getSelectedIndex() != -1) &&
                (powerPlantComboBox.getSelectedIndex() != -1) &&
                (riderComboBox.getSelectedIndex() != -1);
    }
    
    private void addListenersToComboBoxes() {
        for (JComboBox box : selectorMap.keySet()) {
            box.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    final JComboBox fcb = (JComboBox)e.getSource();
                    final String itemSelected = (String)fcb.getSelectedItem();
                    EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            controller.updateMap(selectorMap.get(fcb), itemSelected);
                            updateOutputLabel();
                        }
                    });
                }
            });
        }
    }
   

}
