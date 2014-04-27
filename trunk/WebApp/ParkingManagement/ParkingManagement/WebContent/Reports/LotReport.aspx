<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="LotReport.aspx.cs" Inherits="ParkingManagement.WebContent.Reports.Report1" %>

<%@ Register Assembly="Microsoft.ReportViewer.WebForms, Version=11.0.0.0, Culture=neutral, PublicKeyToken=89845dcd8080cc91" Namespace="Microsoft.Reporting.WebForms" TagPrefix="rsweb" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>NKU Parking Management System</title>
    <script src="../../Scripts/jquery-2.1.0.js"></script>
    <script src="../../Scripts/bootstrap2.js"></script>
    <link href="../../Content/bootstrap2.2.css" rel="stylesheet" />
    <style>
         
         body { background: #FFF url('/../Images/background.jpg') repeat-x; width:975px; margin-left:auto;
                margin-right:auto
         }
         
         
    </style>
</head>
<body>
    <form id="form1" runat="server">
      <div class="header-image" style="margin-left:auto; margin-right:auto; width:970px; ">
             <asp:Image ID="Banner" runat="server" ImageUrl="~/Images/banner.jpg"/>
         </div>
          <div style="margin-top:10px">
             
               <a href="../UsersManagement/UsersManagement.aspx" style="float:right">Main Menu</a>
               <h4>NKU Parking Management System</h4>
           </div>

    <div>
        <h2 style="text-align:center">User Parking Report</h2>
         <asp:GridView ID="gvUsers" runat="server" HorizontalAlign="Center" Width="100%" AutoGenerateColumns="false" 
                          CssClass="table table-hover table-striped" 
                          >
                         <Columns>
                              <asp:BoundField DataField="UserName" HeaderText="UserName" />
                              <asp:BoundField DataField="LastName" HeaderText="Last Name" />
                              <asp:BoundField DataField="FirstName" HeaderText="First Name" />
                              <asp:BoundField DataField="LotId" HeaderText="Lot Id" />
                              <asp:BoundField DataField="DateTimeIn" HeaderText="Date Time In" />
                              <asp:BoundField DataField="DateTimeOut" HeaderText="Date Time Out" />
                              <asp:BoundField DataField="Duration" HeaderText="Duration" />
                             
                         </Columns>

                     </asp:GridView>
    </div>
    </form>
</body>
</html>
