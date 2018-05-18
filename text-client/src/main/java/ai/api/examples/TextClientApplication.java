/**
 * Copyright 2017 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.api.examples;


import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import controller.FxControl;

/**
 * Text client reads requests line by line from standart input.
 */
public class TextClientApplication {

    private static AIDataService dataService;

    private static String sendPrefix = "Deg: ";
    private static String receivePrefix = "Denki: ";
    
    private FxControl controller;

    /**
     * Default exit code in case of error
     */

    
    public TextClientApplication(String[] args, FxControl controller){
    	this.controller = controller;
    	createAiBot(args);
    }

    public void createAiBot(String[] args) {
        if (args.length < 1) {
            showHelp("Please specify API key", 1);
        }
        AIConfiguration configuration = new AIConfiguration(args[0]);
        dataService = new AIDataService(configuration);
    }

    /**
     * @param message
     */
    public void sendRequest(String message) {
        try {
            AIRequest request = new AIRequest(message);
            AIResponse response = dataService.request(request);
            controller.addText(sendPrefix + message);
            if (response.getStatus().getCode() == 200) {
                controller.addText(receivePrefix + response.getResult().getFulfillment().getSpeech());
            } else {
                controller.addText(receivePrefix + response.getStatus().getErrorDetails());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Output application usage information to stdout and exit. No return from function.
     *
     * @param errorMessage Extra error message. Would be printed to stderr if not null and not empty.
     */
    private static void showHelp(String errorMessage, int exitCode) {
        if (errorMessage != null && errorMessage.length() > 0) {
            System.err.println(errorMessage);
            System.err.println();
        }

        System.out.println("MANGLENDE API TOKEN");
        System.out.println();
        System.out.println("SE FIL 'ACCESS TOKENS'");
        System.out.println("Legg Client Access Token inn som program arguments under Run Configurations");
        System.out.println("        See https://docs.api.ai/docs/key-concepts for details");
        System.out.println();
        System.exit(exitCode);
    }
}
