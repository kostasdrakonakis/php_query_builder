 <!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- Sidebar user panel -->
      <div class="user-panel">
        <div class="pull-left image">
          <img src="{{%image%}}" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
          <p>{{%Name%}} {{%Surname%}}</p>
        </div>
      </div>
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <ul class="sidebar-menu">
        <li class="header">{{%MyInformation%}}</li>
        <li>
          <a href="{{%DashboardLink%}}">
            <i class="fa fa-dashboard"></i> <span>{{%Dashboard%}}</span>
          </a>
        </li>
        <li>
          <a href="{{%PernalInfoLink%}}">
            <i class="fa fa-inbox"></i> <span>{{%PersonalDetails%}}</span>
          </a>
        </li>
        <li>
          <a href="{{%FellowTravelersLink%}}">
            <i class="fa fa-users"></i> <span>{{%FellowTravelers%}}</span>
          </a>
        </li>
        <li class="header">{{%MyArrival%}}</li>
        <li>
          <a href="{{%ArrivalLink%}}">
            <i class="fa fa-warning " id="important_info_icon"></i> <span id="important_info_icon">{{%ImportantInformation%}}</span>
          </a>
        </li>
        <li class="header">{{%BedlinnenExtras%}}</li>
        <li>
          <a href="{{%BedLinnenLink%}}">
            <i class="fa fa-bed"></i> <span>{{%Bedlinnen%}}</span>
          </a>
        </li>
        <li class="header">{{%Contact%}}</li>
        <li>
          <a href="{{%ContactFormLink%}}">
            <i class="fa fa-envelope-o"></i> <span>{{%ContactUs%}}</span>
          </a>
        </li>
        <li>
          <a href="{{%ContactMailboxLink%}}">
            <i class="fa fa-envelope"></i> <span>{{%Mailbox%}}</span>
          </a>
        </li>
        <li class="header">{{%Account%}}</li>
        <li>
          <a href="{{%ChangePasswordLink%}}">
            <i class="fa fa-lock"></i> <span>{{%ChangePassword%}}</span>
          </a>
        </li>
        <li>
          <a href="{{%DeleteAccountLink%}}">
            <i class="fa fa-minus-circle"></i> <span>{{%DeleteAccount%}}</span>
          </a>
        </li>
      </ul>
    </section>
    <!-- /.sidebar -->
  </aside>
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    