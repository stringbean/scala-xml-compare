<div class="nav-home">
<a href="$page.home.href$" $if(page.home.active)$class="active"$endif$>
<span class="home-icon">⌂</span>$page.home.title$
</a>
<div class="version-number">
$page.properties.("project.version.short")$
</div>
<div class="scaladoc">
<a href="$page.base$api/index.html"><span class="home-icon">§</span>Scaladocs</a>
</div>
</div>
$groups()$
<div class="nav-toc">
$page.navigation$
</div>