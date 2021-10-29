package com.cs601.project3.slack.api.bot.views;

public class Styles {

    public static String css = """
                      html,
                             body {
                                 font-family: Consolas, Ubuntu Mono, Menlo, Monaco;
                                 max-width: 100vw;
                             }
                     
                             .container {
                                 padding-top: 100px;
                                 max-width: 768px;
                                 width: 90vw;
                                 margin-left: auto;
                                 margin-right: auto;
                                 justify-content: center;
                                 align-items: center;
                             }
                     
                             h1 {
                                 text-decoration: underline;
                             }
                     
                             .input-wrapper {
                                 display: flex;
                                 flex-direction: column;
                             }
                     
                             .input {
                                 padding: 10px 20px;
                                 font-size: 18px;
                                 font-family: Consolas, Ubuntu Mono, Menlo, Monaco;
                             }
                     
                             .input:focus {
                                 outline-color: salmon;
                             }
                     
                             .label {
                                 font-size: 20px;
                                 margin-bottom: 10px;
                             }
                     
                             .search-button {
                                 margin-top: 20px;
                                 font-family: Consolas, Ubuntu Mono, Menlo, Monaco;
                                 font-weight: 700;
                                 padding: 10px 20px;
                                 font-size: 20px;
                                 cursor: pointer;
                                 background-color: rgb(75, 0, 130);
                                 color: white;
                                 border: 1px solid rgb(75, 0, 130);
                                 border-radius: 3px;
                                 margin-bottom: 50px;
                             }
                     
                             .search-button:hover {
                                 background-color: rgb(75, 0, 130, 0.7);
                             }
            """;
}
