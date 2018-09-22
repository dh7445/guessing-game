<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <title>${title}</title>
    <link rel="stylesheet" type="text/css" href="/styles/main.css">
</head>
<body>
    <h1>${title}</h1>

    <div class="body">
      <h4>Make <#if isFirstGuess>a<#else>Another</#if> Guess</h4>
      
      <#if message??>
      <div class="message ${messageType}">${message}</div>
      </#if>
      
      <form action="./guess" method="POST">
        Guess a number between 0 and 9. You have ${guessesLeft} guess<#if
guessesLeft gt 1>es</#if> left.
        <br/>
        <input name="myGuess" />
        <br/><br/>
        <button type="submit">Ok</button>
      </form>
    </div>

</body>
</html>
