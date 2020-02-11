<#import "/spring.ftl" as spring />

<!DOCTYPE html>
<html>
<head>
</head>
<body>
<@spring.bind "event"/>
<form action="/admin/event" method="post">
    Event name:<br>
    <@spring.formInput "event.name"/>
    <@spring.showErrors "<br>"/>
    <br><br>
    Event base price:<br>
    <@spring.formInput "event.basePrice"/>
    <@spring.showErrors "<br>"/>
    <br><br>
    Event rating:<br>
    <@spring.formSingleSelect "event.rating", rating, ""/>
    <@spring.showErrors "<br>"/>
    <br><br>
    <input type="submit" value="Submit">
</form>
</body>
</html>