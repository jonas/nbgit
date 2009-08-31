/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2009 Jonas Fonseca <fonseca@diku.dk>
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file.
 *
 * This particular file is subject to the "Classpath" exception as provided
 * by Sun in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
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
package org.nbgit.util.exclude;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CharacterSequence implements Iterable<String> {

    public static final String WILDCARD_CHARS = "*[?\\";
    private final String except;
    private final int from, to;

    private CharacterSequence(int from, int to, String except) {
        this.from = from - 1;
        this.to = to;
        this.except = except;
    }

    public static CharacterSequence create(int from, int to, String except) {
        return new CharacterSequence(from, to, except);
    }

    public Iterator<String> iterator() {
        return new Generator();
    }

    private class Generator implements Iterator<String> {

        private int current = from;

        private String toString(int value) {
            try {
                return String.valueOf(Character.toChars(value)[0]);
            } catch (Throwable error) {
                throw new IllegalArgumentException("Using " + value);
            }
        }

        private int nextInt() {
            for (int i = current + 1; i <= to; i++) {
                if (!except.contains(toString(i))) {
                    return i;
                }
            }
            return -1;
        }

        public boolean hasNext() {
            return isValidRange(nextInt());
        }

        public String next() {
            current = nextInt();
            if (!isValidRange(current)) {
                throw new NoSuchElementException();
            }
            return toString(current);
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        private boolean isValidRange(int value) {
            return value > from && value <= to;
        }
    }
}
