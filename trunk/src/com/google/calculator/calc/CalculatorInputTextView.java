// Copyright 2008 Google Inc.
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//      http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.calculator.calc;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;


import java.util.Map;

/**
 * TextView for input of mathematical expression. It features autocompletion by word.
 *  
 * @author tulaurent@gmail.com (Laurent Tu)
 */
public class CalculatorInputTextView extends InputTextView {
  
  // Reference of the view displaying the results.
  private CalculatorResultListView resultDisplay;
  
  /**
   * Constructor of CalculatorInputTextView.
   * 
   * @param context
   * @param attrs
   * @param inflateParams
   */
  public CalculatorInputTextView(Context context, AttributeSet attrs,
      Map inflateParams) {
    super(context, attrs, inflateParams);
    setHint("Enter your computation...");
  }
  
  /**
   * Performs computation.
   */
  public void performComputation() {
    CharSequence lineToCompute = getText();
    
    int lineIndex = resultDisplay.addComputationUnit(lineToCompute);
    ComputationResult result = Computer.compute(lineToCompute);
    
    resultDisplay.updateComputationUnitResult(lineIndex, result);

    resetText();
  }

  /**
   * Used to inject the result display view.
   * 
   * @param resultDisplay
   */
  public void setDisplay(CalculatorResultListView resultDisplay) {
    this.resultDisplay = resultDisplay;
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    boolean result = super.onKeyDown(keyCode, event);
    if (event.isCapPressed()) {
      if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
        // Cap + down = previous entry.
        setText(resultDisplay.goPreviousEntry(), BufferType.EDITABLE);
      } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
        // Cap + up = next entry.
        setText(resultDisplay.goNextEntry(), BufferType.EDITABLE);
      } else if (keyCode == KeyEvent.KEYCODE_NEWLINE) {
        // Cap + enter = perform computation.
        performComputation();
      }
    }
    return result;
  }
}
