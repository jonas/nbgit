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
package org.nbgit.ui;

import java.awt.event.ActionEvent;
import javax.swing.Action;
import org.nbgit.StatusInfo;
import org.nbgit.util.GitUtils;
import org.netbeans.modules.versioning.spi.VCSContext;
import org.openide.LifecycleManager;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.actions.NodeAction;

/**
 * Base for all context-sensitive Git actions.
 *
 * @author Padraig O'Briain
 */
public class ContextAction extends NodeAction {

    VCSContext context;

    public ContextAction(String name) {
        putValue(Action.NAME, name);
    }

    @Override
    public String getName() {
        return (String) this.getValue(Action.NAME);
    }

    @Override
    public HelpCtx getHelpCtx() {
        return new HelpCtx(getClass());
    }

    @Override
    protected void performAction(Node[] nodes) {
    }

    @Override
    protected boolean enable(Node[] nodes) {
        return GitUtils.getCurrentContext(nodes).getRootFiles() != null;
    }

    protected VCSContext getContext(Node[] nodes) {
        return GitUtils.getCurrentContext(nodes, getFileEnabledStatus(), getDirectoryEnabledStatus());
    }

    protected int getFileEnabledStatus() {
        return ~0;
    }

    protected int getDirectoryEnabledStatus() {
        return StatusInfo.STATUS_MANAGED & ~StatusInfo.STATUS_NOTVERSIONED_EXCLUDED;
    }

    //protected abstract void performAction(ActionEvent event);
    /** Be sure nobody overwrites */
    @Override
    public final boolean isEnabled() {
        return super.isEnabled();
    }

    /** Be sure nobody overwrites */
    @Override
    public final void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }

    /** Be sure nobody overwrites */
    @Override
    public final void actionPerformed(ActionEvent event) {
        super.actionPerformed(event);
    }

    /** Be sure nobody overwrites */
    @Override
    public final void performAction() {
        super.performAction();
    }

}
