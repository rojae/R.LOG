<%--
  Created by IntelliJ IDEA.
  User: rojae
  Date: 2021/05/17
  Time: 11:56 오후
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<aside class="left-sidebar position-fixed" data-sidebarbg="skin6">
    <!-- Sidebar scroll-->
    <div class="scroll-sidebar">
        <!-- Sidebar navigation-->
        <nav class="sidebar-nav">
            <ul id="sidebarnav">
                <!-- User Profile-->
                <li class="sidebar-item pt-2">
                    <a class="sidebar-link waves-effect waves-dark sidebar-link" href="/manage"
                       aria-expanded="false">
                        <i class="xi xi-dashboard-o xi-1x"></i>
                        <span class="hide-menu">대시보드</span>
                    </a>
                </li>
                <li class="sidebar-item">
                    <a class="sidebar-link waves-effect waves-dark sidebar-link" href="/manage/posts"
                       aria-expanded="false">
                        <i class="xi xi-bars xi-1x"></i>
                        <span class="hide-menu">글 관리</span>
                    </a>
                </li>
                <li class="sidebar-item">
                    <a class="sidebar-link waves-effect waves-dark sidebar-link" href="/manage/categories"
                       aria-expanded="false">
                        <i class="xi xi-list-dot xi-1x"></i>
                        <span class="hide-menu">카테고리 관리</span>
                    </a>
                </li>
                <li class="sidebar-item">
                    <a class="sidebar-link waves-effect waves-dark sidebar-link" href="/manage/error"
                       aria-expanded="false">
                        <i class="xi xi-error xi-1x"></i>
                        <span class="hide-menu">이슈 관리</span>
                    </a>
                </li>
                <li class="sidebar-item">
                    <a class="sidebar-link waves-effect waves-dark sidebar-link" href="/manage/my/info"
                       aria-expanded="false">
                        <i class="xi xi-info-o xi-1x"></i>
                        <span class="hide-menu">관리자 정보</span>
                    </a>
                </li>

                <li class="sidebar-item text-center mt-5">
                    <a class="sidebar-link waves-effect waves-dark sidebar-link" href="/admin/write" target="_self"
                       aria-expanded="false">
                        <i class="xi xi-pen-o xi-1x"></i>
                        <span class="hide-menu">글 작성하기</span>
                    </a>
                </li>
                <li class="sidebar-item text-center">
                    <a class="sidebar-link waves-effect waves-dark sidebar-link" href="/" target="_self"
                       aria-expanded="false">
                        <i class="xi xi-blogger xi-1x"></i>
                        <span class="hide-menu">블로그로 돌아가기</span>
                    </a>
                </li>

            </ul>

        </nav>
        <!-- End Sidebar navigation -->
    </div>
    <!-- End Sidebar scroll-->
</aside>
