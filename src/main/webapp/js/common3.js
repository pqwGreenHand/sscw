function mFoldingMenu(){
  $(".m-folding-menu").hover(function(){
    $(this).addClass("open");
  },function(){
    $(this).removeClass("open");
  })

  $(".m-folding-menu").on("mousemove","*",function(){
    $(this).parents(".m-folding-menu").addClass("open");
  })

  $(".m-folding-menu .menu .item >a").click(function(){
    if($(this).parents(".item").hasClass("open")){
      $(this).parents(".item").removeClass("open");
    }else {
      $(this).parents(".menu").children(".item").removeClass("open");
      $(this).parents(".item").addClass("open");
    }
  })
}

function mPopup(){
  $(".m-popup .close").click(function(){
    $(this).parents(".m-popup").hide();
  })
}

function mHead(){
  $(".m-head .user").hover(function(){
    $(this).addClass("open");
  },function(){
    $(this).removeClass("open");
  })
  $(".m-head .user").on("mousemove","*",function(){
    $(this).parents(".user").addClass("open");
  }),

  $(".m-head .message").hover(function(){
    $(this).addClass("open");
  },function(){
    $(this).removeClass("open");
  })
  $(".m-head .message").on("mousemove","*",function(){
    $(this).parents(".message").addClass("open");
  }),
  $(".m-head .e-mail").hover(function(){
    $(this).addClass("open");
  },function(){
    $(this).removeClass("open");
  })
  $(".m-head .e-mail").on("mousemove","*",function(){
  $(this).parents(".e-mail").addClass("open");
  })

}