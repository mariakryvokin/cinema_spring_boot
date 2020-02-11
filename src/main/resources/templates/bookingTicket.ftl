<#import "/spring.ftl" as spring />
<#setting locale="en_EN">
<html>
<head></head>
<body>
<table border=1>
    <thead>
    <tr>
        <td>Event ID</td>
        <td>Event name</td>
        <td>Auditorium name</td>
        <td>Air date</td>
        <td>Numbers of available seats</td>
    </tr>
    </thead>
     <#list freeSeatsEntrySet as eventHasAuditoriumFreeSeats>
      <tbody>
      <tr>
          <td>${eventHasAuditoriumFreeSeats.getKey().event.getId()}</td>
          <td>${eventHasAuditoriumFreeSeats.getKey().event.getName()}</td>
          <td>${eventHasAuditoriumFreeSeats.getKey().auditorium.name}</td>
          <td>${eventHasAuditoriumFreeSeats.getKey().getAirDate()}</td>
          <td><#list eventHasAuditoriumFreeSeats.getValue() as freeSeatsList>
              ${freeSeatsList}<#sep>; </#sep></#list></td>
          <td></td>
      </tr>
      </tbody>
     </#list>
</table>

<form action="/app/booking" method="post">
    User email(if registered):<br>
    <@spring.formInput "ticket.user.email"/>
    <@spring.showErrors "<br>"/>
    <br><br>
    User id(if NOT registered):<br>
    <@spring.formInput "ticket.user.id"/>
    <@spring.showErrors "<br>"/>
    <br><br>
    Seat:<br>
    <@spring.formInput "ticket.seat"/>
    <@spring.showErrors "<br>"/>
    <br><br>
    Auditorium name:<br>
    <@spring.formInput "ticket.eventHasAuditorium.auditorium.name"/>
    <@spring.showErrors "<br>"/>
    <br><br>
    Air date:<br>
    <@spring.formInput "ticket.eventHasAuditorium.airDate"/>
    <@spring.showErrors "<br>"/>
    <br><br>
    Event ID:<br>
    <@spring.formInput "ticket.eventHasAuditorium.event.id"/>
    <@spring.showErrors "<br>"/>
    <br><br>
    <input type="submit" value="Submit">
</form>

</body>
</html>