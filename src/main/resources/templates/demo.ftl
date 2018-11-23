<!DOCTYPE html>
<html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>This is a login page</title>

</head>
<body>
<form method="post" action="/index/commit">
  <#if Session['1234567']??>
  <input type="hidden" name="token" value="${Session['1234567']}">
  </#if>
  <label for="username">username:</label><input id="username" name="username">
  <label for="password">password:</label><input id="password" name="password">
  <input type="submit" value="登录">
</form>
</body>
<script>

</script>
</html>