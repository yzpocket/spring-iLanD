<?php 

$js_array = [ 'js/member.js' ];

include 'inc_header.php'; 

?>        

        <main class="p-3 border rounded-2">
          <h6 class="text-center mt-3">회원 약관 및 개인정보 취급방침 동의</h6>
          <h6 class="h6 mt-4">회원 약관</h6>
          <textarea name="" id="" cols="30" rows="10" class="form-control">
          Lorem ipsum dolor, sit amet consectetur adipisicing elit. Delectus vero fugit eveniet cum aliquid ratione assumenda mollitia at, voluptates magnam nisi modi, quas expedita temporibus. Consequatur atque optio quos sunt tempora quod iusto, possimus impedit, molestiae cupiditate sint explicabo, quas fuga. Aliquid dolorum quo placeat deserunt vero temporibus minima id? Placeat voluptas cumque magni maxime minus corporis nulla perspiciatis cupiditate dolorum, ut excepturi vero quisquam id animi! Sequi, necessitatibus. Tenetur tempore voluptas ipsum veritatis perferendis fugiat voluptate, omnis, illo ipsam nemo cumque numquam exercitationem reprehenderit temporibus deserunt minus recusandae impedit ea doloribus facilis libero? Excepturi temporibus error reprehenderit provident dolor sit expedita labore doloremque animi culpa distinctio pariatur enim, nulla sequi. Animi iste doloremque accusantium. Recusandae itaque blanditiis corporis dolorem! Inventore, nam? Debitis quidem odio ut rem, deserunt facere dicta non? Voluptatum explicabo tenetur maiores, sint voluptate placeat natus fugit temporibus, eveniet eos sed repellat! Inventore, earum impedit eaque sapiente, reprehenderit libero exercitationem modi in nisi dolorum, soluta illum dolores excepturi consequuntur vitae distinctio alias ipsa. Temporibus nemo reprehenderit eveniet, dolorum, quos inventore quibusdam, alias et dolore delectus omnis porro voluptatibus exercitationem. Incidunt possimus eaque odio. Quam beatae commodi, sint, iusto tenetur neque delectus et fugiat exercitationem earum, odit nisi magnam! Id debitis incidunt ea numquam, doloremque dignissimos assumenda eos, eum ratione esse nostrum laboriosam obcaecati tenetur at! Ipsa officiis sint, fuga rem dolor deleniti necessitatibus dolores! Quas quidem nesciunt eaque ducimus eius iusto ea nemo officiis ratione amet, nihil assumenda fugit mollitia delectus maxime blanditiis reiciendis magni distinctio. Ad ea laborum rem doloremque voluptatibus esse, unde atque a iure soluta officiis consequatur, officia provident facilis. Voluptatibus delectus illo quidem suscipit! Iure voluptatem a nobis facere, autem repudiandae aut reprehenderit alias quisquam voluptate placeat ea temporibus in iste, voluptatibus sed vitae, eos velit doloribus assumenda sint? Eveniet asperiores modi atque.
          </textarea>

          <div class="form-check mt-2">
            <input class="form-check-input" type="checkbox" value="1" id="chk_member1">
            <label class="form-check-label" for="chk_member1">
              위 약관에 동의하시겠습니까?
            </label>
          </div>

          <h6 class="mt-3">개인정보 취급방침</h6>
          <textarea name="" id="" cols="30" rows="10" class="form-control">
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Debitis quod repellat ratione quis laborum culpa, totam provident. Earum excepturi, veniam cumque adipisci nulla quas facilis rerum quam natus itaque labore error exercitationem magni id atque voluptate vitae eaque. Ad et labore explicabo placeat tempore eveniet ipsum sit eaque natus modi amet in eum, maxime, sed exercitationem obcaecati fugit quasi assumenda velit eius odio accusantium. Dicta doloribus corporis eveniet, odio laboriosam nostrum nam amet adipisci ipsa. Voluptatum eius soluta aliquid ab illo quae consequuntur ducimus consequatur tempora. Aliquam harum asperiores error libero illo. Commodi quia hic enim illo aliquid distinctio velit alias debitis voluptatem eos culpa harum facilis assumenda possimus id, nobis numquam. Architecto quas eum nesciunt dignissimos quis, cum quos quae maiores maxime, saepe temporibus placeat? Pariatur provident, similique praesentium repellat quidem odit repudiandae veniam ipsum laudantium architecto incidunt dolor ullam quo iste impedit voluptatibus doloremque amet enim voluptatem! Iusto qui incidunt odio assumenda eligendi mollitia. Molestiae praesentium quod explicabo quaerat voluptas, ea ipsam tenetur aliquam vitae molestias cumque facilis id saepe deleniti nihil, dolorem voluptate eum officiis officia soluta. Distinctio saepe vitae quos beatae voluptatibus commodi iure atque, cupiditate illum ratione eveniet incidunt, perspiciatis error modi sit dignissimos maxime voluptates adipisci? A totam neque corrupti saepe consectetur rem perferendis nam sunt veniam libero amet quasi unde eius consequatur rerum repellat, nemo officiis. Esse, quaerat unde? Aliquam, maxime? Tempora vero qui, dignissimos facere mollitia esse minus minima quaerat maxime a culpa deleniti quasi eaque. Corrupti ducimus velit vero quidem quod eaque quibusdam. Ratione, sapiente impedit ad nobis aliquam dolor sequi cupiditate nemo voluptates. Labore magni pariatur consectetur nemo, aperiam explicabo in sequi dolores iste odit, accusantium aliquid eligendi id provident minus exercitationem excepturi veniam dignissimos repudiandae numquam obcaecati praesentium qui quia fuga. In qui ratione cumque, officia asperiores adipisci laudantium?
          </textarea>

          <div class="form-check mt-2">
            <input class="form-check-input" type="checkbox" value="1" id="chk_member2">
            <label class="form-check-label" for="chk_membe2">
              위 개인정보 취급방침에 동의하시겠습니까?
            </label>
          </div>

          <div class="mt-3 d-flex justify-content-center gap-2">
            <button class="btn btn-primary w-50" id="btn_member">회원가입</button>
            <button class="btn btn-outline-secondary w-50">가입취소</button>
          </div>

          <form method="post" name="stipluation_form" action="member_input.php" style="display: none;">
            <input type="hidden" name="chk" value="0">
          </form>
        
        </main>

<?php include 'inc_footer.php'; ?>