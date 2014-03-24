<%@ Page Language="C#" AutoEventWireup="true" MasterPageFile="~/Site.Master" CodeBehind="Reports.aspx.cs" Inherits="ParkingManagement.WebContent.Reports.Reports" %>

<asp:Content runat="server" ID="FeaturedContent" ContentPlaceHolderID="FeaturedContent">
     <section class="featured">
         <div class="content-wrapper">
           <h1>Reports</h1>
         <ul>
             <li>To include:<ul>
                 <li>Usage: Available space percentage of lot</li>
                 <li>User report: how long user has been parked in a particular spot</li>
                 </ul>
             </li>
         </ul>
        </div>
    </section>
</asp:Content>