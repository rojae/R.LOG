<div id="layoutDefault_footer">
    <footer class="footer pt-10 pb-5 mt-auto bg-dark footer-dark">
        <div class="container">
            <div class="row">
                <div class="col-lg-3">
                    <div class="footer-brand">R.LOG</div>
                    <div class="mb-3">Blog and Rojae's Log</div>
                    <div class="icon-list-social mb-5">
                        <a class="icon-list-social-link" href="https://www.instagram.com/rojae96"><i class="fab fa-instagram"></i></a>
                        <a class="icon-list-social-link" href="https://www.facebook.com/skdltmwotjd"><i class="fab fa-facebook"></i></a>
                        <a class="icon-list-social-link" href="https://github.com/rojae"><i class="fab fa-github"></i></a>
                    </div>
                </div>
                <div class="col-lg-9">
                    <div class="row">
                        <!-- 1 Depth -->
                        <th:block th:each="item : ${menu.subCategories}">
                            <div class="col-lg-3 col-md-6 mb-5 mb-md-0">
                                <div class="text-uppercase-expanded text-xs mb-4" th:text="${item.categoryName}"></div>
                                <ul class="list-unstyled mb-0">
                                    <!-- 2 Depth -->
                                    <th:block th:if="${item.subCategories != null}">
                                        <th:block th:each="subItem : ${item.subCategories}">
                                            <!-- 3 Depth -->
                                            <th:block th:if="${subItem.subCategories != null}">
                                                <th:block th:each="last : ${subItem.subCategories}">
                                                    <li class="mb-2">
                                                        <a th:href="@{/category/{categoryId}/posts(id=${last.categoryId})}" th:text="${last.categoryName}"></a>
                                                    </li>
                                                </th:block>
                                            </th:block>
                                        </th:block>
                                    </th:block>
                                </ul>
                            </div>
                        </th:block>

                        <div class="col-lg-3 col-md-6">
                            <div class="text-uppercase-expanded text-xs mb-4">기타</div>
                            <ul class="list-unstyled mb-0">
                                <li class="mb-2"><a href="/guestbook">방명록</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

            <hr class="my-5"/>
            <div class="row align-items-center">
                <div class="col-md-6 small">Copyright &copy; R.LOG 2021</div>
                <div class="col-md-6 text-md-right small">
                    <a href="#!">Privacy Policy</a>
                    &middot;
                    <a href="#!">Terms & Conditions</a>
                </div>
            </div>
        </div>
    </footer>
</div>
