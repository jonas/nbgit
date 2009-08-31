/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 * Portions Copyright 2008 Alexander Coles (Ikonoklastik Productions).
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */
package org.nbgit.ui.update;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingUtilities;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.nbgit.GitModuleConfig;
import org.nbgit.util.GitCommand;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;

/**
 *
 * @author  Padraig O'Briain
 */
public class RevertModificationsPanel extends javax.swing.JPanel {

    private File repository;
    private File[] revertFiles;
    private RequestProcessor.Task refreshViewTask;
    private Thread refreshViewThread;
    private static final RequestProcessor rp = new RequestProcessor("GitRevert", 1);  // NOI18N
    private static final int GIT_REVERT_TARGET_LIMIT = 100;
    private List<String[]> revisionMap;

    /** Creates new form ReverModificationsPanel */
    public RevertModificationsPanel(File repo, File[] files) {
        repository = repo;
        revertFiles = files;
        refreshViewTask = rp.create(new RefreshViewTask());
        initComponents();
        refreshViewTask.schedule(0);
    }

    public File[] getRevertFiles() {
        return revertFiles;
    }

    public boolean isBackupRequested() {
        return doBackupChxBox.isSelected();
    }

    public String getSelectedRevision() {
        String revStr = (String) revisionsComboBox.getSelectedItem();
        if (revStr != null) {
            if (revStr.equals(NbBundle.getMessage(RevertModificationsPanel.class, "MSG_Revision_Default")) || // NOI18N
                    revStr.equals(NbBundle.getMessage(RevertModificationsPanel.class, "MSG_Fetching_Revisions"))) // NOI18N
            {
                revStr = null;
            } else if (revisionMap != null) {
                for (String[] entry : revisionMap) {
                    if (entry[0].equals(revStr)) {
                        revStr = entry[1];
                        break;
                    }
                }
            }
        }
        return revStr;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        revisionsLabel = new javax.swing.JLabel();
        revisionsComboBox = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        doBackupChxBox = new javax.swing.JCheckBox();

        revisionsLabel.setLabelFor(revisionsComboBox);
        org.openide.awt.Mnemonics.setLocalizedText(revisionsLabel, org.openide.util.NbBundle.getMessage(RevertModificationsPanel.class, "RevertModificationsPanel.revisionsLabel.text")); // NOI18N

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 11));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(RevertModificationsPanel.class, "RevertModificationsPanel.infoLabel.text")); // NOI18N

        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(RevertModificationsPanel.class, "RevertModificationsPanel.infoLabel2.text")); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Options"));

        org.openide.awt.Mnemonics.setLocalizedText(doBackupChxBox, org.openide.util.NbBundle.getMessage(RevertModificationsPanel.class, "RevertModificationsPanel.doBackupChxBox.text")); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(doBackupChxBox)
                .addContainerGap(159, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(doBackupChxBox)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        doBackupChxBox.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(RevertModificationsPanel.class, "ACSD_doBackupChxBox")); // NOI18N

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(47, 47, 47)
                        .add(revisionsLabel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(revisionsComboBox, 0, 334, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel2)
                            .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
                            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(4, 4, 4)
                .add(jLabel2)
                .add(29, 29, 29)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(revisionsLabel)
                    .add(revisionsComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        revisionsComboBox.getAccessibleContext().setAccessibleDescription(org.openide.util.NbBundle.getMessage(RevertModificationsPanel.class, "ACSD_revisionsComboBox")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Must NOT be run from AWT.
     */
    private void setupModels()
    {
        // XXX attach Cancelable hook
        final ProgressHandle ph = ProgressHandleFactory.createHandle(NbBundle.getMessage(RevertModificationsPanel.class, "MSG_Refreshing_Revert_Versions")); // NOI18N
        try {
            boolean doBackup = GitModuleConfig.getDefault().getBackupOnRevertModifications();
            doBackupChxBox.setSelected(doBackup);

            Set<String> initialRevsSet = new LinkedHashSet<String>();
            initialRevsSet.add(NbBundle.getMessage(RevertModificationsPanel.class, "MSG_Fetching_Revisions")); // NOI18N
            ComboBoxModel targetsModel = new DefaultComboBoxModel(new Vector<String>(initialRevsSet));
            revisionsComboBox.setModel(targetsModel);
            refreshViewThread = Thread.currentThread();
            Thread.interrupted();  // clear interupted status
            ph.start();

            refreshRevisions();
        } finally {
            SwingUtilities.invokeLater(new Runnable() {

                public void run()
                {
                    ph.finish();
                    refreshViewThread = null;
                }

            });
        }
    }

    private void refreshRevisions()
    {
        revisionMap = GitCommand.getRevisionsForFile(repository, revertFiles, GIT_REVERT_TARGET_LIMIT);

        Set<String> targetRevsSet = new LinkedHashSet<String>();

        if (revisionMap == null)
            targetRevsSet.add(NbBundle.getMessage(RevertModificationsPanel.class, "MSG_Revision_Default"));
        else
            for (String[] entry : revisionMap) {
                targetRevsSet.add(entry[0]);
            }
        ComboBoxModel targetsModel = new DefaultComboBoxModel(new Vector<String>(targetRevsSet));
        revisionsComboBox.setModel(targetsModel);

        if (targetRevsSet.size() > 0)
            revisionsComboBox.setSelectedIndex(0);
    }

    private class RefreshViewTask implements Runnable {

        public void run()
        {
            setupModels();
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox doBackupChxBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox revisionsComboBox;
    private javax.swing.JLabel revisionsLabel;
    // End of variables declaration//GEN-END:variables
}
