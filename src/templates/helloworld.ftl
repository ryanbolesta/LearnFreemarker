<#import "lib/utils.ftl" as u>

<@u.page>
  <p>${exampleObject.name}  ${exampleObject.number}</p>

  <ul>
    <#list systems as system>
      <li>${system_index + 1}. ${system.name} ${system.number}</li>
    </#list>
  </ul>

  <#-- Just another example of using a macro: -->
  <@u.otherExample p1=11 p2=22 />
</@u.page>