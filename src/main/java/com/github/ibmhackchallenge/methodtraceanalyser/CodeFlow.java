/*
 * Copyright 2018 Manjot Sidhu <manjot.techie@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.ibmhackchallenge.methodtraceanalyser;

import javax.swing.JFrame;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxEdgeStyle;
import com.mxgraph.view.mxGraph;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class CodeFlow extends JFrame {

    public CodeFlow(ArrayList parsedText, ArrayList<Integer> parsedSequence) throws IOException {
        super("Code Flow");

        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        graph.getModel().beginUpdate();

        try {
            Map<String, Object> style = graph.getStylesheet().getDefaultEdgeStyle();
            style.put(mxConstants.STYLE_EDGE, mxEdgeStyle.SegmentConnector);

            int xOffset = 15;
            int yOffset = 15;
            int xMultiplier = 0;

            for (int itr = 1; itr < parsedText.size(); itr++) {
                xMultiplier = (itr == 2) ? 0 : xMultiplier;
                Object v1 = graph.insertVertex(parent, null, parsedText.get(itr-1), xOffset + (xMultiplier * (parsedSequence.get(itr-1)-1)), yOffset, ((String) parsedText.get(itr-1)).length() * 6.5,
                        30);
                xMultiplier = (itr == 2) ? 100 : xMultiplier;
                
                yOffset = yOffset + 50;
                Object v2 = graph.insertVertex(parent, null, parsedText.get(itr), xOffset + (xMultiplier * (parsedSequence.get(itr)-1)), yOffset, ((String) parsedText.get(itr)).length() * 6.5,
                                30);
                graph.insertEdge(parent, null, "", v1, v2, mxConstants.EDGESTYLE_TOPTOBOTTOM);
                xMultiplier = (itr == 1) ? 100 : xMultiplier;
            }

        } finally {
            graph.getModel().endUpdate();
        }

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);
    }
}
