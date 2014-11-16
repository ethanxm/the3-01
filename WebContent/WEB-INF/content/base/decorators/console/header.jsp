<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/base/taglibs.jsp"%>
	<!-- Static navbar -->
	<div class="navbar navbar-default navbar-static-top" role="navigation">
		<div class="container" style="width: 970px;">
			<div class="navbar-header">
				<!--           <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse"> -->
				<!--             <span class="sr-only">Toggle navigation</span> -->
				<!--             <span class="icon-bar"></span> -->
				<!--             <span class="icon-bar"></span> -->
				<!--             <span class="icon-bar"></span> -->
				<!--           </button> -->
				<a class="navbar-brand" href="${root}/console/home">ImEthan</a>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li><a href="${root}/console/home">Home</a></li>
					<li><a href="#about">About</a></li>
					<li><a href="#contact">Contact</a></li>
					
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">CMS <b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="${root}/console/cms/channel">Channel</a></li>
							<li><a href="${root}/console/cms/article">Article</a></li>
							<!--                 <li class="divider"></li> -->
							<!--                 <li class="dropdown-header">Nav header</li> -->
							<!--                 <li><a href="#">Separated link</a></li> -->
							<!--                 <li><a href="#">One more separated link</a></li> -->
						</ul>
					</li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">SECURITY<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="${root}/console/security/user">User</a></li>
							<li><a href="${root}/console/security/role">Role</a></li>
							<li><a href="${root}/console/security/resource">Resource</a></li>
						</ul>
					</li>
				</ul>
<!-- 				          <ul class="nav navbar-nav navbar-right"> -->
<!-- 				            <li><a href="#">Default</a></li> -->
<!-- 				            <li><a href="#">Static top</a></li> -->
<!-- 				            <li><a href="#">Fixed top</a></li> -->
<!-- 				          </ul> -->

				<div class="navbar-form navbar-right" >
					<shiro:guest>  
						<a type="submit" class="btn btn-default btn-sm" href="${root}/console/toSignin">Sign in</a>
					</shiro:guest>
					<shiro:user>  
						Hello, <shiro:principal/>!  
						<a type="submit" class="btn btn-default btn-sm" href="${root}/console/signout">Sign out</a>
					</shiro:user>
				</div>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>