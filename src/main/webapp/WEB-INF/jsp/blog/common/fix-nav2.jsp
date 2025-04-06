<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-marketing navbar-expand-lg bg-white navbar-light">
    <div class="container">
        <a class="navbar-brand text-primary" href="/">R.LOG</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><i
                data-feather="menu"></i></button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ml-auto mr-lg-5">
                <li class="nav-item dropdown dropdown-xl no-caret">
                    <a class="nav-link dropdown-toggle" id="navbarDropdownDemos" href="#" role="button"
                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">자바프로그래밍<i
                            class="fas fa-chevron-right dropdown-arrow"></i></a>
                    <div class="dropdown-menu dropdown-menu-right animated--fade-in-up mr-lg-n25 mr-xl-n15"
                         aria-labelledby="navbarDropdownDemos">
                        <div class="row no-gutters">
                            <div class="col-lg-5 p-lg-3 bg-img-cover overlay overlay-primary overlay-70 d-none d-lg-block"
                                 style="background-image: url('assets/img/backgrounds/bg-dropdown-xl.jpg')">
                                <div class="d-flex h-100 w-100 align-items-center justify-content-center">
                                    <div class="text-white text-center z-1">
                                        <div class="mb-3">Multipurpose landing pages for a variety of projects.</div>
                                        <a class="btn btn-white btn-sm text-primary font-weight-500" href="index.html">View
                                            All</a>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-7 p-lg-5">
                                <div class="row">
                                    <div class="col-lg-6">
                                        <h6 class="dropdown-header text-primary">Applications</h6>
                                        <a class="dropdown-item" href="landing-app-mobile.html">Mobile App</a><a
                                            class="dropdown-item" href="landing-app-desktop.html">Desktop App</a>
                                        <div class="dropdown-divider border-0"></div>
                                        <h6 class="dropdown-header text-primary">Business</h6>
                                        <a class="dropdown-item" href="landing-multipurpose.html">Multipurpose</a><a
                                            class="dropdown-item" href="landing-agency.html">Agency</a><a
                                            class="dropdown-item" href="landing-press.html">Press</a><a
                                            class="dropdown-item" href="landing-directory.html">Directory</a><a
                                            class="dropdown-item" href="landing-rental.html">Rental</a><a
                                            class="dropdown-item" href="landing-real-estate.html">Real Estate</a><a
                                            class="dropdown-item" href="landing-classifieds.html">Classifieds</a>
                                        <div class="dropdown-divider border-0"></div>
                                        <h6 class="dropdown-header text-primary">Lead Generation</h6>
                                        <a class="dropdown-item" href="landing-lead-capture.html">Lead Capture</a>
                                        <div class="dropdown-divider border-0 d-lg-none"></div>
                                    </div>
                                    <div class="col-lg-6">
                                        <h6 class="dropdown-header text-primary">Personal</h6>
                                        <a class="dropdown-item" href="landing-resume.html">Resume</a><a
                                            class="dropdown-item" href="landing-portfolio.html">Portfolio</a>
                                        <div class="dropdown-divider border-0"></div>
                                        <h6 class="dropdown-header text-primary">Header Styles</h6>
                                        <a class="dropdown-item" href="header-basic.html">Basic</a><a
                                            class="dropdown-item" href="header-basic-signup.html">Basic (Signup)</a><a
                                            class="dropdown-item" href="header-graphic.html">Graphic</a><a
                                            class="dropdown-item" href="header-graphic-signup.html">Graphic (Signup)</a><a
                                            class="dropdown-item" href="header-inner-page.html">Inner Page</a><a
                                            class="dropdown-item" href="header-nav-only.html">Nav Only</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </li>
                <li class="nav-item dropdown dropdown-xl no-caret">
                    <a class="nav-link dropdown-toggle" id="navbarDropdownPages" href="#" role="button"
                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">개발문서<i
                            class="fas fa-chevron-right dropdown-arrow"></i></a>
                    <div class="dropdown-menu dropdown-menu-right mr-lg-n20 mr-xl-n15 animated--fade-in-up"
                         aria-labelledby="navbarDropdownPages">
                        <div class="row no-gutters">
                            <div class="col-lg-4 p-lg-5">
                                <h6 class="dropdown-header text-primary">Company</h6>
                                <a class="dropdown-item" href="page-basic.html">Basic Page</a><a class="dropdown-item"
                                                                                                 href="page-company-about.html">About</a><a
                                    class="dropdown-item" href="page-company-pricing.html">Pricing</a><a
                                    class="dropdown-item" href="page-company-contact.html">Contact</a><a
                                    class="dropdown-item" href="page-company-team.html">Team</a><a class="dropdown-item"
                                                                                                   href="page-company-terms.html">Terms</a>
                                <div class="dropdown-divider border-0"></div>
                                <h6 class="dropdown-header text-primary">Support</h6>
                                <a class="dropdown-item" href="page-help-center.html">Help Center</a><a
                                    class="dropdown-item" href="page-help-knowledgebase.html">Knowledgebase</a><a
                                    class="dropdown-item" href="page-help-message-center.html">Message Center</a><a
                                    class="dropdown-item" href="page-help-support-ticket.html">Support Ticket</a>
                                <div class="dropdown-divider border-0 d-lg-none"></div>
                            </div>
                            <div class="col-lg-4 p-lg-5">
                                <h6 class="dropdown-header text-primary">Careers</h6>
                                <a class="dropdown-item" href="page-careers-overview.html">Careers List</a><a
                                    class="dropdown-item" href="page-careers-listing.html">Position Details</a>
                                <div class="dropdown-divider border-0"></div>
                                <h6 class="dropdown-header text-primary">Blog</h6>
                                <a class="dropdown-item" href="page-blog-overview.html">Overview</a><a
                                    class="dropdown-item" href="page-blog-post.html">Post</a><a class="dropdown-item"
                                                                                                href="page-blog-archive.html">Archive</a>
                                <div class="dropdown-divider border-0"></div>
                                <h6 class="dropdown-header text-primary">Portfolio</h6>
                                <a class="dropdown-item" href="page-portfolio-grid.html">Grid</a><a
                                    class="dropdown-item" href="page-portfolio-large-grid.html">Large Grid</a><a
                                    class="dropdown-item" href="page-portfolio-masonry.html">Masonry</a><a
                                    class="dropdown-item" href="page-portfolio-case-study.html">Case Study</a><a
                                    class="dropdown-item" href="page-portfolio-project.html">Project</a>
                                <div class="dropdown-divider border-0 d-lg-none"></div>
                            </div>
                            <div class="col-lg-4 p-lg-5">
                                <h6 class="dropdown-header text-primary">Error</h6>
                                <a class="dropdown-item" href="page-error-400.html">400 Error</a><a
                                    class="dropdown-item" href="page-error-401.html">401 Error</a><a
                                    class="dropdown-item" href="page-error-404-1.html">404 Error (Option 1)</a><a
                                    class="dropdown-item" href="page-error-404-2.html">404 Error (Option 2)</a><a
                                    class="dropdown-item" href="page-error-500.html">500 Error</a><a
                                    class="dropdown-item" href="page-error-503.html">503 Error</a><a
                                    class="dropdown-item" href="page-error-504.html">504 Error</a>
                            </div>
                        </div>
                    </div>
                </li>
                <li class="nav-item dropdown no-caret">
                    <a class="nav-link dropdown-toggle" id="navbarDropdownDocs" href="#" role="button"
                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">일상<i
                            class="fas fa-chevron-right dropdown-arrow"></i></a>
                    <div class="dropdown-menu dropdown-menu-right animated--fade-in-up"
                         aria-labelledby="navbarDropdownDocs">
                        <a class="dropdown-item py-3" href="https://docs.startbootstrap.com/sb-ui-kit-pro/quickstart"
                           target="_blank"
                        >
                            <div class="icon-stack bg-primary-soft text-primary mr-4"><i data-feather="book-open"></i>
                            </div>
                            <div>
                                <div class="small text-gray-500">Documentation</div>
                                Usage instructions and reference
                            </div>
                        </a
                        >
                        <div class="dropdown-divider m-0"></div>
                        <a class="dropdown-item py-3" href="https://docs.startbootstrap.com/sb-ui-kit-pro/components"
                           target="_blank"
                        >
                            <div class="icon-stack bg-primary-soft text-primary mr-4"><i data-feather="code"></i></div>
                            <div>
                                <div class="small text-gray-500">Components</div>
                                Code snippets and reference
                            </div>
                        </a
                        >
                        <div class="dropdown-divider m-0"></div>
                        <a class="dropdown-item py-3" href="https://docs.startbootstrap.com/sb-ui-kit-pro/changelog"
                           target="_blank"
                        >
                            <div class="icon-stack bg-primary-soft text-primary mr-4"><i data-feather="file-text"></i>
                            </div>
                            <div>
                                <div class="small text-gray-500">Changelog</div>
                                Updates and changes
                            </div>
                        </a
                        >
                    </div>
                </li>
                <li class="nav-item"><a class="nav-link" href="/guestbook">방명록</a></li>
            </ul>
            <form class="form-inline form-inline-sm" action="/index">
                <input name="keyword" class="form-control form-control-sm mr-sm-2" type="search" placeholder="Search" aria-label="Search" value="<c:out value = '${keyword}'/>">
                <button class="btn btn-sm rounded-sm btn-outline-primary my-2 my-sm-0" type="submit"><i class="fas fa-search"></i></button>
            </form>

            <sec:authorize access="isAnonymous()">
                <a class="btn font-weight-500 ml-lg-2 btn-primary btn-sm" href="#" onclick="goLogin();">로그인<i class="ml-2" data-feather="arrow-right"></i></a>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <a class="btn font-weight-500 ml-lg-2 btn-primary btn-sm" href="#" onclick="goManage();">관리자<i class="ml-2" data-feather="arrow-right"></i></a>
                </sec:authorize>
                <a class="btn font-weight-500 ml-lg-2 btn-primary btn-sm" href="#" onclick="myInfo();">내 정보<i class="ml-2" data-feather="arrow-right"></i></a>
                <a class="btn font-weight-500 ml-lg-2 btn-danger btn-sm" href="#" onclick="goLogout();">로그아웃<i class="ml-2" data-feather="arrow-right"></i></a>
            </sec:authorize>

        </div>
    </div>
</nav>